package com.upsaude.service.impl;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.api.response.PreNatalResponse;
import com.upsaude.entity.PreNatal;
import com.upsaude.enums.StatusPreNatalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PreNatalMapper;
import com.upsaude.repository.PreNatalRepository;
import com.upsaude.service.PreNatalService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PreNatalServiceImpl implements PreNatalService {

    private final PreNatalRepository preNatalRepository;
    private final PreNatalMapper preNatalMapper;

    @Override
    @Transactional
    @CacheEvict(value = "prenatal", allEntries = true)
    public PreNatalResponse criar(PreNatalRequest request) {
        log.debug("Criando novo pré-natal");

        PreNatal preNatal = preNatalMapper.fromRequest(request);
        preNatal.setActive(true);

        if (preNatal.getStatusPreNatal() == null) {
            preNatal.setStatusPreNatal(StatusPreNatalEnum.EM_ACOMPANHAMENTO);
        }

        PreNatal preNatalSalvo = preNatalRepository.save(preNatal);
        log.info("Pré-natal criado com sucesso. ID: {}", preNatalSalvo.getId());

        return preNatalMapper.toResponse(preNatalSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "prenatal", key = "#id")
    public PreNatalResponse obterPorId(UUID id) {
        log.debug("Buscando pré-natal por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }

        PreNatal preNatal = preNatalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pré-natal não encontrado com ID: " + id));

        return preNatalMapper.toResponse(preNatal);
    }

    @Override
    public Page<PreNatalResponse> listar(Pageable pageable) {
        log.debug("Listando pré-natais paginados");

        Page<PreNatal> preNatais = preNatalRepository.findAll(pageable);
        return preNatais.map(preNatalMapper::toResponse);
    }

    @Override
    public Page<PreNatalResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando pré-natais por estabelecimento: {}", estabelecimentoId);

        Page<PreNatal> preNatais = preNatalRepository.findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, pageable);
        return preNatais.map(preNatalMapper::toResponse);
    }

    @Override
    public List<PreNatalResponse> listarPorPaciente(UUID pacienteId) {
        log.debug("Listando pré-natais por paciente: {}", pacienteId);

        List<PreNatal> preNatais = preNatalRepository.findByPacienteIdOrderByDataInicioAcompanhamentoDesc(pacienteId);
        return preNatais.stream().map(preNatalMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Page<PreNatalResponse> listarEmAcompanhamento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando pré-natais em acompanhamento: {}", estabelecimentoId);

        Page<PreNatal> preNatais = preNatalRepository.findByStatusPreNatalAndEstabelecimentoId(
                StatusPreNatalEnum.EM_ACOMPANHAMENTO, estabelecimentoId, pageable);
        return preNatais.map(preNatalMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "prenatal", key = "#id")
    public PreNatalResponse atualizar(UUID id, PreNatalRequest request) {
        log.debug("Atualizando pré-natal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }

        PreNatal preNatalExistente = preNatalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pré-natal não encontrado com ID: " + id));

        atualizarDadosPreNatal(preNatalExistente, request);

        PreNatal preNatalAtualizado = preNatalRepository.save(preNatalExistente);
        log.info("Pré-natal atualizado com sucesso. ID: {}", preNatalAtualizado.getId());

        return preNatalMapper.toResponse(preNatalAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "prenatal", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo pré-natal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }

        PreNatal preNatal = preNatalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pré-natal não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(preNatal.getActive())) {
            throw new BadRequestException("Pré-natal já está inativo");
        }

        preNatal.setActive(false);
        preNatalRepository.save(preNatal);
        log.info("Pré-natal excluído (desativado) com sucesso. ID: {}", id);
    }

    private void atualizarDadosPreNatal(PreNatal preNatal, PreNatalRequest request) {
        PreNatal preNatalAtualizado = preNatalMapper.fromRequest(request);

        UUID idOriginal = preNatal.getId();
        com.upsaude.entity.Tenant tenantOriginal = preNatal.getTenant();
        Boolean activeOriginal = preNatal.getActive();
        java.time.OffsetDateTime createdAtOriginal = preNatal.getCreatedAt();

        BeanUtils.copyProperties(preNatalAtualizado, preNatal);

        preNatal.setId(idOriginal);
        preNatal.setTenant(tenantOriginal);
        preNatal.setActive(activeOriginal);
        preNatal.setCreatedAt(createdAtOriginal);
    }
}
