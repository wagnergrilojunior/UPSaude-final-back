package com.upsaude.service.impl;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.PlanejamentoFamiliarResponse;
import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PlanejamentoFamiliarMapper;
import com.upsaude.repository.PlanejamentoFamiliarRepository;
import com.upsaude.service.PlanejamentoFamiliarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de gerenciamento de Planejamento Familiar.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlanejamentoFamiliarServiceImpl implements PlanejamentoFamiliarService {

    private final PlanejamentoFamiliarRepository planejamentoFamiliarRepository;
    private final PlanejamentoFamiliarMapper planejamentoFamiliarMapper;

    @Override
    @Transactional
    @CacheEvict(value = "planejamentofamiliar", allEntries = true)
    public PlanejamentoFamiliarResponse criar(PlanejamentoFamiliarRequest request) {
        log.debug("Criando novo planejamento familiar");

        validarDadosBasicos(request);

        PlanejamentoFamiliar planejamentoFamiliar = planejamentoFamiliarMapper.fromRequest(request);
        planejamentoFamiliar.setActive(true);

        if (planejamentoFamiliar.getAcompanhamentoAtivo() == null) {
            planejamentoFamiliar.setAcompanhamentoAtivo(true);
        }

        PlanejamentoFamiliar planejamentoFamiliarSalvo = planejamentoFamiliarRepository.save(planejamentoFamiliar);
        log.info("Planejamento familiar criado com sucesso. ID: {}", planejamentoFamiliarSalvo.getId());

        return planejamentoFamiliarMapper.toResponse(planejamentoFamiliarSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "planejamentofamiliar", key = "#id")
    public PlanejamentoFamiliarResponse obterPorId(UUID id) {
        log.debug("Buscando planejamento familiar por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do planejamento familiar é obrigatório");
        }

        PlanejamentoFamiliar planejamentoFamiliar = planejamentoFamiliarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Planejamento familiar não encontrado com ID: " + id));

        return planejamentoFamiliarMapper.toResponse(planejamentoFamiliar);
    }

    @Override
    public Page<PlanejamentoFamiliarResponse> listar(Pageable pageable) {
        log.debug("Listando planejamentos familiares paginados");

        Page<PlanejamentoFamiliar> planejamentos = planejamentoFamiliarRepository.findAll(pageable);
        return planejamentos.map(planejamentoFamiliarMapper::toResponse);
    }

    @Override
    public Page<PlanejamentoFamiliarResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando planejamentos familiares por estabelecimento: {}", estabelecimentoId);

        Page<PlanejamentoFamiliar> planejamentos = planejamentoFamiliarRepository.findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, pageable);
        return planejamentos.map(planejamentoFamiliarMapper::toResponse);
    }

    @Override
    public List<PlanejamentoFamiliarResponse> listarPorPaciente(UUID pacienteId) {
        log.debug("Listando planejamentos familiares por paciente: {}", pacienteId);

        List<PlanejamentoFamiliar> planejamentos = planejamentoFamiliarRepository.findByPacienteIdOrderByDataInicioAcompanhamentoDesc(pacienteId);
        return planejamentos.stream().map(planejamentoFamiliarMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Page<PlanejamentoFamiliarResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando planejamentos familiares ativos: {}", estabelecimentoId);

        Page<PlanejamentoFamiliar> planejamentos = planejamentoFamiliarRepository.findByAcompanhamentoAtivoAndEstabelecimentoId(true, estabelecimentoId, pageable);
        return planejamentos.map(planejamentoFamiliarMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "planejamentofamiliar", key = "#id")
    public PlanejamentoFamiliarResponse atualizar(UUID id, PlanejamentoFamiliarRequest request) {
        log.debug("Atualizando planejamento familiar. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do planejamento familiar é obrigatório");
        }

        validarDadosBasicos(request);

        PlanejamentoFamiliar planejamentoExistente = planejamentoFamiliarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Planejamento familiar não encontrado com ID: " + id));

        atualizarDadosPlanejamentoFamiliar(planejamentoExistente, request);

        PlanejamentoFamiliar planejamentoAtualizado = planejamentoFamiliarRepository.save(planejamentoExistente);
        log.info("Planejamento familiar atualizado com sucesso. ID: {}", planejamentoAtualizado.getId());

        return planejamentoFamiliarMapper.toResponse(planejamentoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "planejamentofamiliar", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo planejamento familiar. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do planejamento familiar é obrigatório");
        }

        PlanejamentoFamiliar planejamentoFamiliar = planejamentoFamiliarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Planejamento familiar não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(planejamentoFamiliar.getActive())) {
            throw new BadRequestException("Planejamento familiar já está inativo");
        }

        planejamentoFamiliar.setActive(false);
        planejamentoFamiliarRepository.save(planejamentoFamiliar);
        log.info("Planejamento familiar excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(PlanejamentoFamiliarRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do planejamento familiar são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }

    private void atualizarDadosPlanejamentoFamiliar(PlanejamentoFamiliar planejamento, PlanejamentoFamiliarRequest request) {
        PlanejamentoFamiliar planejamentoAtualizado = planejamentoFamiliarMapper.fromRequest(request);

        UUID idOriginal = planejamento.getId();
        com.upsaude.entity.Tenant tenantOriginal = planejamento.getTenant();
        Boolean activeOriginal = planejamento.getActive();
        java.time.OffsetDateTime createdAtOriginal = planejamento.getCreatedAt();

        BeanUtils.copyProperties(planejamentoAtualizado, planejamento);

        planejamento.setId(idOriginal);
        planejamento.setTenant(tenantOriginal);
        planejamento.setActive(activeOriginal);
        planejamento.setCreatedAt(createdAtOriginal);
    }
}

