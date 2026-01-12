package com.upsaude.service.impl.api.clinica.prontuario;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.clinica.prontuario.AlergiaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.AlergiaPacienteResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.prontuario.AlergiaPaciente;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.prontuario.AlergiaPacienteMapper;
import com.upsaude.repository.clinica.prontuario.AlergiaPacienteRepository;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.repository.alergia.AlergenoRepository;
import com.upsaude.repository.alergia.ReacaoAdversaCatalogoRepository;
import com.upsaude.repository.alergia.CriticidadeAlergiaRepository;
import com.upsaude.repository.alergia.CategoriaAgenteAlergiaRepository;
import com.upsaude.service.api.clinica.prontuario.AlergiaPacienteService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiaPacienteServiceImpl implements AlergiaPacienteService {

    private final AlergiaPacienteRepository repository;
    private final ProntuarioRepository prontuarioRepository;
    private final Cid10SubcategoriasRepository cid10SubcategoriasRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final AlergiaPacienteMapper mapper;

    private final AlergenoRepository alergenoRepository;
    private final ReacaoAdversaCatalogoRepository reacaoAdversaCatalogoRepository;
    private final CriticidadeAlergiaRepository criticidadeAlergiaRepository;
    private final CategoriaAgenteAlergiaRepository categoriaAgenteAlergiaRepository;

    @Override
    @Transactional
    @SuppressWarnings("null")
    public AlergiaPacienteResponse criar(AlergiaPacienteRequest request) {
        log.debug("Criando nova alergia do paciente");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            AlergiaPaciente entity = mapper.fromRequest(request);
            entity.setActive(true);
            entity.setTenant(tenant);

            Prontuario prontuario = prontuarioRepository.findByIdAndTenant(request.getProntuario(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Prontuário não encontrado"));
            entity.setProntuario(prontuario);

            if (request.getDiagnosticoRelacionado() != null) {
                Cid10Subcategorias diagnostico = cid10SubcategoriasRepository
                        .findById(request.getDiagnosticoRelacionado())
                        .orElseThrow(() -> new NotFoundException("Diagnóstico CID-10 não encontrado"));
                entity.setDiagnosticoRelacionado(diagnostico);
            }

            if (request.getAlergeno() != null) {
                entity.setAlergeno(alergenoRepository.findById(request.getAlergeno())
                        .orElseThrow(() -> new NotFoundException("Alérgeno não encontrado no catálogo")));
            }

            if (request.getReacaoAdversaCatalogo() != null) {
                entity.setReacaoAdversaCatalogo(
                        reacaoAdversaCatalogoRepository.findById(request.getReacaoAdversaCatalogo())
                                .orElseThrow(() -> new NotFoundException("Reação adversa não encontrada no catálogo")));
            }

            if (request.getCriticidade() != null) {
                entity.setCriticidade(criticidadeAlergiaRepository.findById(request.getCriticidade())
                        .orElseThrow(() -> new NotFoundException("Nível de criticidade não encontrado no catálogo")));
            }

            if (request.getCategoriaAgente() != null) {
                entity.setCategoriaAgente(categoriaAgenteAlergiaRepository.findById(request.getCategoriaAgente())
                        .orElseThrow(() -> new NotFoundException("Categoria de agente não encontrada no catálogo")));
            }

            AlergiaPaciente saved = repository.save(Objects.requireNonNull(entity));
            AlergiaPacienteResponse response = mapper.toResponse(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRONTUARIOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.prontuario(tenantId, prontuario.getId()));
                cache.evict(key);
            }

            log.info("Alergia do paciente criada com sucesso. ID: {}", saved.getId());
            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao criar alergia do paciente", e);
            throw new InternalServerErrorException("Erro ao criar alergia do paciente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public AlergiaPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando alergia do paciente por ID: {}", id);
        if (id == null) {
            throw new BadRequestException("ID da alergia é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        AlergiaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Alergia do paciente não encontrada"));

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlergiaPacienteResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<AlergiaPaciente> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlergiaPacienteResponse> listarPorProntuario(UUID prontuarioId, Pageable pageable) {
        if (prontuarioId == null) {
            throw new BadRequestException("ID do prontuário é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<AlergiaPaciente> page = repository.findByProntuarioIdAndTenantId(prontuarioId, tenantId, pageable);
        return page.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    @SuppressWarnings("null")
    public AlergiaPacienteResponse atualizar(UUID id, AlergiaPacienteRequest request) {
        log.debug("Atualizando alergia do paciente. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            AlergiaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Alergia do paciente não encontrada"));

            mapper.updateFromRequest(request, entity);

            if (request.getDiagnosticoRelacionado() != null) {
                Cid10Subcategorias diagnostico = cid10SubcategoriasRepository
                        .findById(request.getDiagnosticoRelacionado())
                        .orElseThrow(() -> new NotFoundException("Diagnóstico CID-10 não encontrado"));
                entity.setDiagnosticoRelacionado(diagnostico);
            } else {
                entity.setDiagnosticoRelacionado(null);
            }

            if (request.getAlergeno() != null) {
                entity.setAlergeno(alergenoRepository.findById(request.getAlergeno())
                        .orElseThrow(() -> new NotFoundException("Alérgeno não encontrado no catálogo")));
            } else {
                entity.setAlergeno(null);
            }

            if (request.getReacaoAdversaCatalogo() != null) {
                entity.setReacaoAdversaCatalogo(
                        reacaoAdversaCatalogoRepository.findById(request.getReacaoAdversaCatalogo())
                                .orElseThrow(() -> new NotFoundException("Reação adversa não encontrada no catálogo")));
            } else {
                entity.setReacaoAdversaCatalogo(null);
            }

            if (request.getCriticidade() != null) {
                entity.setCriticidade(criticidadeAlergiaRepository.findById(request.getCriticidade())
                        .orElseThrow(() -> new NotFoundException("Nível de criticidade não encontrado no catálogo")));
            } else {
                entity.setCriticidade(null);
            }

            if (request.getCategoriaAgente() != null) {
                entity.setCategoriaAgente(categoriaAgenteAlergiaRepository.findById(request.getCategoriaAgente())
                        .orElseThrow(() -> new NotFoundException("Categoria de agente não encontrada no catálogo")));
            } else {
                entity.setCategoriaAgente(null);
            }

            AlergiaPaciente saved = repository.save(entity);
            AlergiaPacienteResponse response = mapper.toResponse(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRONTUARIOS);
            if (cache != null && entity.getProntuario() != null) {
                Object key = Objects
                        .requireNonNull((Object) CacheKeyUtil.prontuario(tenantId, entity.getProntuario().getId()));
                cache.evict(key);
            }

            log.info("Alergia do paciente atualizada com sucesso. ID: {}", id);
            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar alergia do paciente. Exception: {}",
                    e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar alergia do paciente", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar alergia do paciente. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao atualizar alergia do paciente", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public void excluir(UUID id) {
        log.debug("Excluindo alergia do paciente. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            AlergiaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Alergia do paciente não encontrada"));

            UUID prontuarioId = entity.getProntuario() != null ? entity.getProntuario().getId() : null;
            repository.delete(entity);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRONTUARIOS);
            if (cache != null && prontuarioId != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.prontuario(tenantId, prontuarioId));
                cache.evict(key);
            }

            log.info("Alergia do paciente excluída com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir alergia do paciente. Exception: {}",
                    e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir alergia do paciente", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir alergia do paciente. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir alergia do paciente", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public void inativar(UUID id) {
        log.debug("Inativando alergia do paciente. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            AlergiaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Alergia do paciente não encontrada"));

            entity.setActive(false);
            repository.save(entity);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRONTUARIOS);
            if (cache != null && entity.getProntuario() != null) {
                Object key = Objects
                        .requireNonNull((Object) CacheKeyUtil.prontuario(tenantId, entity.getProntuario().getId()));
                cache.evict(key);
            }

            log.info("Alergia do paciente inativada com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar alergia do paciente. Exception: {}",
                    e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar alergia do paciente", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao inativar alergia do paciente. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar alergia do paciente", e);
        }
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
