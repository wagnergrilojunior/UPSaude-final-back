package com.upsaude.service.impl;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import com.upsaude.entity.ResponsavelLegal;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ResponsavelLegalMapper;
import com.upsaude.repository.ResponsavelLegalRepository;
import com.upsaude.service.ResponsavelLegalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponsavelLegalServiceImpl implements ResponsavelLegalService {

    private final ResponsavelLegalRepository repository;
    private final ResponsavelLegalMapper mapper;

    @Override
    @Transactional
    @CacheEvict(value = "responsavellegal", allEntries = true)
    public ResponsavelLegalResponse criar(ResponsavelLegalRequest request) {
        log.debug("Criando responsável legal para paciente: {}", request.getPacienteId());

        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do responsável legal é obrigatório");
        }

        repository.findByPacienteId(request.getPacienteId())
                .ifPresent(d -> {
                    throw new ConflictException("Responsável legal já existe para este paciente");
                });

        if (StringUtils.hasText(request.getCpf()) && !request.getCpf().matches("^\\d{11}$")) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        ResponsavelLegal entity = mapper.fromRequest(request);
        entity.setActive(true);

        ResponsavelLegal saved = repository.save(entity);
        log.info("Responsável legal criado. ID: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    public ResponsavelLegalResponse obterPorId(UUID id) {
        log.debug("Buscando responsável legal por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public ResponsavelLegalResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando responsável legal por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        ResponsavelLegal entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado para o paciente: " + pacienteId));

        return mapper.toResponse(entity);
    }

    @Override
    public Page<ResponsavelLegalResponse> listar(Pageable pageable) {
        log.debug("Listando responsáveis legais paginados");

        Page<ResponsavelLegal> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "responsavellegal", key = "#id")
    public ResponsavelLegalResponse atualizar(UUID id, ResponsavelLegalRequest request) {
        log.debug("Atualizando responsável legal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do responsável legal é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        if (request.getPacienteId() != null && !request.getPacienteId().equals(entity.getPaciente().getId())) {
            repository.findByPacienteId(request.getPacienteId())
                    .ifPresent(d -> {
                        if (!d.getId().equals(id)) {
                            throw new ConflictException("Responsável legal já existe para este paciente");
                        }
                    });
        }

        if (StringUtils.hasText(request.getCpf()) && !request.getCpf().matches("^\\d{11}$")) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        atualizarDados(entity, request);
        ResponsavelLegal updated = repository.save(entity);
        log.info("Responsável legal atualizado. ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "responsavellegal", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo responsável legal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Responsável legal já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Responsável legal excluído. ID: {}", id);
    }

    private void atualizarDados(ResponsavelLegal entity, ResponsavelLegalRequest request) {
        ResponsavelLegal updated = mapper.fromRequest(request);
        
        entity.setNome(updated.getNome());
        entity.setCpf(updated.getCpf());
        entity.setTelefone(updated.getTelefone());
        entity.setTipoResponsavel(updated.getTipoResponsavel());
        entity.setAutorizacaoUsoDadosLGPD(updated.getAutorizacaoUsoDadosLGPD());
        entity.setAutorizacaoResponsavel(updated.getAutorizacaoResponsavel());
    }
}

