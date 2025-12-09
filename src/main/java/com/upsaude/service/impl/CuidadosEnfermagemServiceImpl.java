package com.upsaude.service.impl;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CuidadosEnfermagemMapper;
import com.upsaude.repository.CuidadosEnfermagemRepository;
import com.upsaude.service.CuidadosEnfermagemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Cuidados de Enfermagem.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemServiceImpl implements CuidadosEnfermagemService {

    private final CuidadosEnfermagemRepository cuidadosEnfermagemRepository;
    private final CuidadosEnfermagemMapper cuidadosEnfermagemMapper;

    @Override
    @Transactional
    @CacheEvict(value = "cuidadosenfermagem", allEntries = true)
    public CuidadosEnfermagemResponse criar(CuidadosEnfermagemRequest request) {
        log.debug("Criando novo cuidado de enfermagem");

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        CuidadosEnfermagem cuidadosEnfermagem = cuidadosEnfermagemMapper.fromRequest(request);
        cuidadosEnfermagem.setActive(true);

        CuidadosEnfermagem cuidadosSalvo = cuidadosEnfermagemRepository.save(cuidadosEnfermagem);
        log.info("Cuidado de enfermagem criado com sucesso. ID: {}", cuidadosSalvo.getId());

        return cuidadosEnfermagemMapper.toResponse(cuidadosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "cuidadosenfermagem", key = "#id")
    public CuidadosEnfermagemResponse obterPorId(UUID id) {
        log.debug("Buscando cuidado de enfermagem por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do cuidado de enfermagem é obrigatório");
        }

        CuidadosEnfermagem cuidadosEnfermagem = cuidadosEnfermagemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuidado de enfermagem não encontrado com ID: " + id));

        return cuidadosEnfermagemMapper.toResponse(cuidadosEnfermagem);
    }

    @Override
    public Page<CuidadosEnfermagemResponse> listar(Pageable pageable) {
        log.debug("Listando cuidados de enfermagem paginados");

        Page<CuidadosEnfermagem> cuidados = cuidadosEnfermagemRepository.findAll(pageable);
        return cuidados.map(cuidadosEnfermagemMapper::toResponse);
    }

    @Override
    public Page<CuidadosEnfermagemResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando cuidados de enfermagem por estabelecimento: {}", estabelecimentoId);

        Page<CuidadosEnfermagem> cuidados = cuidadosEnfermagemRepository.findByEstabelecimentoIdOrderByDataHoraDesc(estabelecimentoId, pageable);
        return cuidados.map(cuidadosEnfermagemMapper::toResponse);
    }

    @Override
    public Page<CuidadosEnfermagemResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando cuidados de enfermagem por paciente: {}", pacienteId);

        Page<CuidadosEnfermagem> cuidados = cuidadosEnfermagemRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId, pageable);
        return cuidados.map(cuidadosEnfermagemMapper::toResponse);
    }

    @Override
    public Page<CuidadosEnfermagemResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando cuidados de enfermagem por profissional: {}", profissionalId);

        Page<CuidadosEnfermagem> cuidados = cuidadosEnfermagemRepository.findByProfissionalIdOrderByDataHoraDesc(profissionalId, pageable);
        return cuidados.map(cuidadosEnfermagemMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cuidadosenfermagem", key = "#id")
    public CuidadosEnfermagemResponse atualizar(UUID id, CuidadosEnfermagemRequest request) {
        log.debug("Atualizando cuidado de enfermagem. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do cuidado de enfermagem é obrigatório");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        CuidadosEnfermagem cuidadosExistente = cuidadosEnfermagemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuidado de enfermagem não encontrado com ID: " + id));

        atualizarDadosCuidadosEnfermagem(cuidadosExistente, request);

        CuidadosEnfermagem cuidadosAtualizado = cuidadosEnfermagemRepository.save(cuidadosExistente);
        log.info("Cuidado de enfermagem atualizado com sucesso. ID: {}", cuidadosAtualizado.getId());

        return cuidadosEnfermagemMapper.toResponse(cuidadosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cuidadosenfermagem", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo cuidado de enfermagem. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do cuidado de enfermagem é obrigatório");
        }

        CuidadosEnfermagem cuidadosEnfermagem = cuidadosEnfermagemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuidado de enfermagem não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(cuidadosEnfermagem.getActive())) {
            throw new BadRequestException("Cuidado de enfermagem já está inativo");
        }

        cuidadosEnfermagem.setActive(false);
        cuidadosEnfermagemRepository.save(cuidadosEnfermagem);
        log.info("Cuidado de enfermagem excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.
    // Os campos 'paciente', 'profissional' e 'tipoCuidado' já têm @NotNull no Request.

    private void atualizarDadosCuidadosEnfermagem(CuidadosEnfermagem cuidados, CuidadosEnfermagemRequest request) {
        CuidadosEnfermagem cuidadosAtualizado = cuidadosEnfermagemMapper.fromRequest(request);

        UUID idOriginal = cuidados.getId();
        com.upsaude.entity.Tenant tenantOriginal = cuidados.getTenant();
        Boolean activeOriginal = cuidados.getActive();
        java.time.OffsetDateTime createdAtOriginal = cuidados.getCreatedAt();

        BeanUtils.copyProperties(cuidadosAtualizado, cuidados);

        cuidados.setId(idOriginal);
        cuidados.setTenant(tenantOriginal);
        cuidados.setActive(activeOriginal);
        cuidados.setCreatedAt(createdAtOriginal);
    }
}

