package com.upsaude.service.impl;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.api.response.IntegracaoGovResponse;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.IntegracaoGovMapper;
import com.upsaude.repository.IntegracaoGovRepository;
import com.upsaude.service.IntegracaoGovService;
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
public class IntegracaoGovServiceImpl implements IntegracaoGovService {

    private final IntegracaoGovRepository repository;
    private final IntegracaoGovMapper mapper;

    @Override
    @Transactional
    @CacheEvict(value = "integracaogov", allEntries = true)
    public IntegracaoGovResponse criar(IntegracaoGovRequest request) {
        log.debug("Criando integração gov para paciente: {}", request.getPacienteId());

        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        repository.findByPacienteId(request.getPacienteId())
                .ifPresent(d -> {
                    throw new ConflictException("Integração gov já existe para este paciente");
                });

        IntegracaoGov entity = mapper.fromRequest(request);
        entity.setActive(true);

        IntegracaoGov saved = repository.save(entity);
        log.info("Integração gov criada. ID: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    public IntegracaoGovResponse obterPorId(UUID id) {
        log.debug("Buscando integração gov por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        IntegracaoGov entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Integração gov não encontrada com ID: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public IntegracaoGovResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando integração gov por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        IntegracaoGov entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Integração gov não encontrada para o paciente: " + pacienteId));

        return mapper.toResponse(entity);
    }

    @Override
    public Page<IntegracaoGovResponse> listar(Pageable pageable) {
        log.debug("Listando integrações gov paginadas");

        Page<IntegracaoGov> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "integracaogov", key = "#id")
    public IntegracaoGovResponse atualizar(UUID id, IntegracaoGovRequest request) {
        log.debug("Atualizando integração gov. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        IntegracaoGov entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Integração gov não encontrada com ID: " + id));

        if (request.getPacienteId() != null && !request.getPacienteId().equals(entity.getPaciente().getId())) {
            repository.findByPacienteId(request.getPacienteId())
                    .ifPresent(d -> {
                        if (!d.getId().equals(id)) {
                            throw new ConflictException("Integração gov já existe para este paciente");
                        }
                    });
        }

        atualizarDados(entity, request);
        IntegracaoGov updated = repository.save(entity);
        log.info("Integração gov atualizada. ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "integracaogov", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo integração gov. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        IntegracaoGov entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Integração gov não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Integração gov já está inativa");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Integração gov excluída. ID: {}", id);
    }

    private void atualizarDados(IntegracaoGov entity, IntegracaoGovRequest request) {
        IntegracaoGov updated = mapper.fromRequest(request);
        
        entity.setUuidRnds(updated.getUuidRnds());
        entity.setIdIntegracaoGov(updated.getIdIntegracaoGov());
        entity.setDataSincronizacaoGov(updated.getDataSincronizacaoGov());
        entity.setIneEquipe(updated.getIneEquipe());
        entity.setMicroarea(updated.getMicroarea());
        entity.setCnesEstabelecimentoOrigem(updated.getCnesEstabelecimentoOrigem());
        entity.setOrigemCadastro(updated.getOrigemCadastro());
    }
}

