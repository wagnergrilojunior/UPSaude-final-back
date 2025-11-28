package com.upsaude.service.impl;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DadosClinicosBasicosMapper;
import com.upsaude.repository.DadosClinicosBasicosRepository;
import com.upsaude.service.DadosClinicosBasicosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosServiceImpl implements DadosClinicosBasicosService {

    private final DadosClinicosBasicosRepository repository;
    private final DadosClinicosBasicosMapper mapper;

    @Override
    @Transactional
    public DadosClinicosBasicosResponse criar(DadosClinicosBasicosRequest request) {
        log.debug("Criando dados clínicos básicos para paciente: {}", request.getPacienteId());

        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        repository.findByPacienteId(request.getPacienteId())
                .ifPresent(d -> {
                    throw new ConflictException("Dados clínicos básicos já existem para este paciente");
                });

        DadosClinicosBasicos entity = mapper.fromRequest(request);
        entity.setActive(true);

        DadosClinicosBasicos saved = repository.save(entity);
        log.info("Dados clínicos básicos criados. ID: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    public DadosClinicosBasicosResponse obterPorId(UUID id) {
        log.debug("Buscando dados clínicos básicos por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        DadosClinicosBasicos entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public DadosClinicosBasicosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados clínicos básicos por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        DadosClinicosBasicos entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados para o paciente: " + pacienteId));

        return mapper.toResponse(entity);
    }

    @Override
    public Page<DadosClinicosBasicosResponse> listar(Pageable pageable) {
        log.debug("Listando dados clínicos básicos paginados");

        Page<DadosClinicosBasicos> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional
    public DadosClinicosBasicosResponse atualizar(UUID id, DadosClinicosBasicosRequest request) {
        log.debug("Atualizando dados clínicos básicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        DadosClinicosBasicos entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

        if (request.getPacienteId() != null && !request.getPacienteId().equals(entity.getPaciente().getId())) {
            repository.findByPacienteId(request.getPacienteId())
                    .ifPresent(d -> {
                        if (!d.getId().equals(id)) {
                            throw new ConflictException("Dados clínicos básicos já existem para este paciente");
                        }
                    });
        }

        atualizarDados(entity, request);
        DadosClinicosBasicos updated = repository.save(entity);
        log.info("Dados clínicos básicos atualizados. ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo dados clínicos básicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        DadosClinicosBasicos entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Dados clínicos básicos já estão inativos");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Dados clínicos básicos excluídos. ID: {}", id);
    }

    private void atualizarDados(DadosClinicosBasicos entity, DadosClinicosBasicosRequest request) {
        DadosClinicosBasicos updated = mapper.fromRequest(request);
        
        entity.setGestante(updated.getGestante());
        entity.setFumante(updated.getFumante());
        entity.setAlcoolista(updated.getAlcoolista());
        entity.setUsuarioDrogas(updated.getUsuarioDrogas());
        entity.setHistoricoViolencia(updated.getHistoricoViolencia());
        entity.setAcompanhamentoPsicossocial(updated.getAcompanhamentoPsicossocial());
    }
}

