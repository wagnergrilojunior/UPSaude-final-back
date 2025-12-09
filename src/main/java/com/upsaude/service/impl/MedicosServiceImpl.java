package com.upsaude.service.impl;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicosMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.service.MedicosService;
import com.upsaude.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medicos.
 * Usa anotações JPA para delegar responsabilidades ao framework.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicosServiceImpl implements MedicosService {

    private final MedicosRepository medicosRepository;
    private final MedicosMapper medicosMapper;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    @CacheEvict(value = "medicos", allEntries = true)
    public MedicosResponse criar(MedicosRequest request) {
        log.debug("Criando novo médico. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar médico com request nulo");
            throw new BadRequestException("Dados do médico são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            // Obtém o tenant do usuário autenticado (obrigatório para BaseEntityWithoutEstabelecimento)
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar médicos.");
            }

            // Validações de duplicatas antes de criar
            validarCrmUnicoPorTenant(null, request, tenant);
            validarCpfUnicoPorTenant(null, request, tenant);

            Medicos medicos = medicosMapper.fromRequest(request);
            medicos.setActive(true);
            medicos.setTenant(tenant);

            // Processar relacionamentos - JPA gerencia a persistência automaticamente
            processarRelacionamentos(medicos, request);

            Medicos medicosSalvo = medicosRepository.save(medicos);
            log.info("Médico criado com sucesso. ID: {}", medicosSalvo.getId());

            return medicosMapper.toResponse(medicosSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar médico. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar médico. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao persistir médico", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar médico. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "medicos", key = "#id")
    public MedicosResponse obterPorId(UUID id) {
        log.debug("Buscando médico por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Medicos medicos = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            log.debug("Médico encontrado. ID: {}", id);
            return medicosMapper.toResponse(medicos);
        } catch (NotFoundException e) {
            log.warn("Médico não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar médico. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao buscar médico", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar médico. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicosResponse> listar(Pageable pageable) {
        log.debug("Listando médicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Medicos> medicos = medicosRepository.findAll(pageable);
            log.debug("Listagem de médicos concluída. Total de elementos: {}", medicos.getTotalElements());
            return medicos.map(medicosMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar médicos. Pageable: {}, Exception: {}", pageable, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao listar médicos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao listar médicos. Pageable: {}, Exception: {}", pageable, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public MedicosResponse atualizar(UUID id, MedicosRequest request) {
        log.debug("Atualizando médico. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de médico. ID: {}", id);
            throw new BadRequestException("Dados do médico são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Medicos medicosExistente = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            // Obtém o tenant do médico existente para validações
            Tenant tenant = medicosExistente.getTenant();
            if (tenant == null) {
                throw new BadRequestException("Médico não possui tenant associado. Não é possível atualizar.");
            }

            // Validações de duplicatas antes de atualizar
            validarCrmUnicoPorTenant(id, request, tenant);
            validarCpfUnicoPorTenant(id, request, tenant);

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            medicosMapper.updateFromRequest(request, medicosExistente);
            
            // Processar relacionamentos - JPA gerencia a persistência automaticamente
            processarRelacionamentos(medicosExistente, request);

            Medicos medicosAtualizado = medicosRepository.save(medicosExistente);
            log.info("Médico atualizado com sucesso. ID: {}", medicosAtualizado.getId());

            return medicosMapper.toResponse(medicosAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar médico não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar médico. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar médico. ID: {}, Request: {}, Exception: {}", id, request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao atualizar médico", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar médico. ID: {}, Request: {}, Exception: {}", id, request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo médico. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Medicos medicos = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(medicos.getActive())) {
                log.warn("Tentativa de excluir médico já inativo. ID: {}", id);
                throw new BadRequestException("Médico já está inativo");
            }

            medicos.setActive(false);
            medicosRepository.save(medicos);
            log.info("Médico excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir médico não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir médico. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir médico. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao excluir médico", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir médico. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw e;
        }
    }

    private void validarDadosBasicos(MedicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do médico são obrigatórios");
        }
    }

    /**
     * Valida se o CRM já está cadastrado para outro médico no mesmo tenant.
     * 
     * @param medicoId ID do médico sendo atualizado (null se for criação)
     * @param request dados do request com CRM e UF
     * @param tenant tenant do médico
     * @throws BadRequestException se já existir outro médico com o mesmo CRM/UF no tenant
     */
    private void validarCrmUnicoPorTenant(UUID medicoId, MedicosRequest request, Tenant tenant) {
        if (request == null || request.getRegistroProfissional() == null) {
            return;
        }

        String crm = request.getRegistroProfissional().getCrm();
        String crmUf = request.getRegistroProfissional().getCrmUf();

        if (!StringUtils.hasText(crm) || !StringUtils.hasText(crmUf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository
                .findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenant(crm, crmUf, tenant);

        if (medicoExistente.isPresent()) {
            Medicos medicoEncontrado = medicoExistente.get();

            // Se for atualização, verifica se o CRM pertence a outro médico
            if (medicoId != null && !medicoEncontrado.getId().equals(medicoId)) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CRM %s/%s neste tenant.", crm, crmUf));
            }

            // Se for criação, sempre lança exceção se encontrar CRM
            if (medicoId == null) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CRM %s/%s neste tenant.", crm, crmUf));
            }
        }
    }

    /**
     * Valida se o CPF já está cadastrado para outro médico no mesmo tenant.
     * 
     * @param medicoId ID do médico sendo atualizado (null se for criação)
     * @param request dados do request com CPF
     * @param tenant tenant do médico
     * @throws BadRequestException se já existir outro médico com o mesmo CPF no tenant
     */
    private void validarCpfUnicoPorTenant(UUID medicoId, MedicosRequest request, Tenant tenant) {
        if (request == null || request.getDadosPessoais() == null) {
            return;
        }

        String cpf = request.getDadosPessoais().getCpf();

        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository.findByDadosPessoaisCpfAndTenant(cpf, tenant);

        if (medicoExistente.isPresent()) {
            Medicos medicoEncontrado = medicoExistente.get();

            // Se for atualização, verifica se o CPF pertence a outro médico
            if (medicoId != null && !medicoEncontrado.getId().equals(medicoId)) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CPF %s neste tenant.", cpf));
            }

            // Se for criação, sempre lança exceção se encontrar CPF
            if (medicoId == null) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CPF %s neste tenant.", cpf));
            }
        }
    }

    // Método removido - agora usa medicosMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente

    /**
     * Processa relacionamentos do médico de forma simplificada.
     * Com as anotações corretas do JPA (cascade, orphanRemoval), o Hibernate 
     * gerencia automaticamente a ordem de salvamento e integridade referencial.
     *
     * Responsabilidades deste método:
     * 1. Buscar entidades relacionadas existentes (apenas validação)
     * 2. Atribuir as referências
     * 3. O JPA/Hibernate cuida do resto (ordem, persistência, cascade)
     *
     * @param medicos entidade Medicos a ser processada
     * @param request dados do request com os UUIDs dos relacionamentos
     */
    private void processarRelacionamentos(Medicos medicos, MedicosRequest request) {
        log.debug("Processando relacionamentos do médico");

        // ESPECIALIDADE (ManyToOne) - Buscar entidade existente
        // Não usa cascade pois especialidades são entidades independentes compartilhadas
        if (request.getEspecialidade() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository
                    .findById(request.getEspecialidade())
                    .orElseThrow(() -> new NotFoundException(
                            "Especialidade médica não encontrada com ID: " + request.getEspecialidade()));
            medicos.setEspecialidade(especialidade);
        } else {
            medicos.setEspecialidade(null);
        }

        // VÍNCULOS E ENDEREÇOS são gerenciados com cascade automático pelo JPA
        // Se necessário adicionar/remover vínculos, isso deve ser feito através de endpoints específicos
        
        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
    }
}
