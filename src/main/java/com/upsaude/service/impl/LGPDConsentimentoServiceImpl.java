package com.upsaude.service.impl;

import com.upsaude.api.request.LGPDConsentimentoRequest;
import com.upsaude.api.response.LGPDConsentimentoResponse;
import com.upsaude.entity.LGPDConsentimento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.LGPDConsentimentoMapper;
import com.upsaude.repository.LGPDConsentimentoRepository;
import com.upsaude.service.LGPDConsentimentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LGPDConsentimentoServiceImpl implements LGPDConsentimentoService {

    private final LGPDConsentimentoRepository repository;
    private final LGPDConsentimentoMapper mapper;

    @Override
    @Transactional
    @CacheEvict(value = "lgpdconsentimento", allEntries = true)
    public LGPDConsentimentoResponse criar(LGPDConsentimentoRequest request) {
        log.debug("Criando consentimento LGPD para paciente: {}", request.getPaciente());

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        repository.findByPacienteId(request.getPaciente())
                .ifPresent(d -> {
                    throw new ConflictException("Consentimento LGPD já existe para este paciente");
                });

        LGPDConsentimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        LGPDConsentimento saved = repository.save(entity);
        log.info("Consentimento LGPD criado. ID: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    public LGPDConsentimentoResponse obterPorId(UUID id) {
        log.debug("Buscando consentimento LGPD por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        LGPDConsentimento entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consentimento LGPD não encontrado com ID: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public LGPDConsentimentoResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando consentimento LGPD por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        LGPDConsentimento entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Consentimento LGPD não encontrado para o paciente: " + pacienteId));

        return mapper.toResponse(entity);
    }

    @Override
    public Page<LGPDConsentimentoResponse> listar(Pageable pageable) {
        log.debug("Listando consentimentos LGPD paginados");

        Page<LGPDConsentimento> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lgpdconsentimento", key = "#id")
    public LGPDConsentimentoResponse atualizar(UUID id, LGPDConsentimentoRequest request) {
        log.debug("Atualizando consentimento LGPD. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        LGPDConsentimento entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consentimento LGPD não encontrado com ID: " + id));

        if (request.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
            repository.findByPacienteId(request.getPaciente())
                    .ifPresent(d -> {
                        if (!d.getId().equals(id)) {
                            throw new ConflictException("Consentimento LGPD já existe para este paciente");
                        }
                    });
        }

        atualizarDados(entity, request);
        LGPDConsentimento updated = repository.save(entity);
        log.info("Consentimento LGPD atualizado. ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lgpdconsentimento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo consentimento LGPD. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        LGPDConsentimento entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consentimento LGPD não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Consentimento LGPD já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Consentimento LGPD excluído. ID: {}", id);
    }

    private void atualizarDados(LGPDConsentimento entity, LGPDConsentimentoRequest request) {
        LGPDConsentimento updated = mapper.fromRequest(request);
        
        entity.setAutorizacaoUsoDados(updated.getAutorizacaoUsoDados());
        entity.setAutorizacaoContatoWhatsApp(updated.getAutorizacaoContatoWhatsApp());
        entity.setAutorizacaoContatoEmail(updated.getAutorizacaoContatoEmail());
        entity.setDataConsentimento(updated.getDataConsentimento());
    }
}

