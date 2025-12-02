package com.upsaude.service.impl;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DadosSociodemograficosMapper;
import com.upsaude.repository.DadosSociodemograficosRepository;
import com.upsaude.service.DadosSociodemograficosService;
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
public class DadosSociodemograficosServiceImpl implements DadosSociodemograficosService {

    private final DadosSociodemograficosRepository repository;
    private final DadosSociodemograficosMapper mapper;

    @Override
    @Transactional
    @CacheEvict(value = "dadossociodemograficos", allEntries = true)
    public DadosSociodemograficosResponse criar(DadosSociodemograficosRequest request) {
        log.debug("Criando dados sociodemográficos para paciente: {}", request.getPacienteId());

        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        repository.findByPacienteId(request.getPacienteId())
                .ifPresent(d -> {
                    throw new ConflictException("Dados sociodemográficos já existem para este paciente");
                });

        DadosSociodemograficos entity = mapper.fromRequest(request);
        entity.setActive(true);

        DadosSociodemograficos saved = repository.save(entity);
        log.info("Dados sociodemográficos criados. ID: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    public DadosSociodemograficosResponse obterPorId(UUID id) {
        log.debug("Buscando dados sociodemográficos por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        DadosSociodemograficos entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public DadosSociodemograficosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados sociodemográficos por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        DadosSociodemograficos entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados para o paciente: " + pacienteId));

        return mapper.toResponse(entity);
    }

    @Override
    public Page<DadosSociodemograficosResponse> listar(Pageable pageable) {
        log.debug("Listando dados sociodemográficos paginados");

        Page<DadosSociodemograficos> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadossociodemograficos", key = "#id")
    public DadosSociodemograficosResponse atualizar(UUID id, DadosSociodemograficosRequest request) {
        log.debug("Atualizando dados sociodemográficos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        DadosSociodemograficos entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id));

        if (request.getPacienteId() != null && !request.getPacienteId().equals(entity.getPaciente().getId())) {
            repository.findByPacienteId(request.getPacienteId())
                    .ifPresent(d -> {
                        if (!d.getId().equals(id)) {
                            throw new ConflictException("Dados sociodemográficos já existem para este paciente");
                        }
                    });
        }

        atualizarDados(entity, request);
        DadosSociodemograficos updated = repository.save(entity);
        log.info("Dados sociodemográficos atualizados. ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadossociodemograficos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo dados sociodemográficos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        DadosSociodemograficos entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Dados sociodemográficos já estão inativos");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Dados sociodemográficos excluídos. ID: {}", id);
    }

    private void atualizarDados(DadosSociodemograficos entity, DadosSociodemograficosRequest request) {
        DadosSociodemograficos updated = mapper.fromRequest(request);
        
        entity.setRacaCor(updated.getRacaCor());
        entity.setNacionalidade(updated.getNacionalidade());
        entity.setPaisNascimento(updated.getPaisNascimento());
        entity.setNaturalidade(updated.getNaturalidade());
        entity.setMunicipioNascimentoIbge(updated.getMunicipioNascimentoIbge());
        entity.setEscolaridade(updated.getEscolaridade());
        entity.setOcupacaoProfissao(updated.getOcupacaoProfissao());
        entity.setSituacaoRua(updated.getSituacaoRua());
        entity.setTempoSituacaoRua(updated.getTempoSituacaoRua());
        entity.setCondicaoMoradia(updated.getCondicaoMoradia());
        entity.setSituacaoFamiliar(updated.getSituacaoFamiliar());
    }
}

