package com.upsaude.service.impl;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.AcaoPromocaoPrevencaoResponse;
import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AcaoPromocaoPrevencaoMapper;
import com.upsaude.repository.AcaoPromocaoPrevencaoRepository;
import com.upsaude.service.AcaoPromocaoPrevencaoService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoServiceImpl implements AcaoPromocaoPrevencaoService {

    private final AcaoPromocaoPrevencaoRepository acaoPromocaoPrevencaoRepository;
    private final AcaoPromocaoPrevencaoMapper acaoPromocaoPrevencaoMapper;

    @Override
    @Transactional
    @CacheEvict(value = "acaopromocaoprevencao", allEntries = true)
    public AcaoPromocaoPrevencaoResponse criar(AcaoPromocaoPrevencaoRequest request) {
        log.debug("Criando nova ação de promoção e prevenção");

        AcaoPromocaoPrevencao acaoPromocaoPrevencao = acaoPromocaoPrevencaoMapper.fromRequest(request);
        acaoPromocaoPrevencao.setActive(true);

        if (acaoPromocaoPrevencao.getStatusAcao() == null) {
            acaoPromocaoPrevencao.setStatusAcao("PLANEJADA");
        }

        if (acaoPromocaoPrevencao.getAcaoContinua() == null) {
            acaoPromocaoPrevencao.setAcaoContinua(false);
        }

        AcaoPromocaoPrevencao acaoSalva = acaoPromocaoPrevencaoRepository.save(acaoPromocaoPrevencao);
        log.info("Ação de promoção e prevenção criada com sucesso. ID: {}", acaoSalva.getId());

        return acaoPromocaoPrevencaoMapper.toResponse(acaoSalva);
    }

    @Override
    @Transactional
    @Cacheable(value = "acaopromocaoprevencao", key = "#id")
    public AcaoPromocaoPrevencaoResponse obterPorId(UUID id) {
        log.debug("Buscando ação de promoção e prevenção por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da ação de promoção e prevenção é obrigatório");
        }

        AcaoPromocaoPrevencao acaoPromocaoPrevencao = acaoPromocaoPrevencaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ação de promoção e prevenção não encontrada com ID: " + id));

        return acaoPromocaoPrevencaoMapper.toResponse(acaoPromocaoPrevencao);
    }

    @Override
    public Page<AcaoPromocaoPrevencaoResponse> listar(Pageable pageable) {
        log.debug("Listando ações de promoção e prevenção paginadas");

        Page<AcaoPromocaoPrevencao> acoes = acaoPromocaoPrevencaoRepository.findAll(pageable);
        return acoes.map(acaoPromocaoPrevencaoMapper::toResponse);
    }

    @Override
    public Page<AcaoPromocaoPrevencaoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando ações de promoção e prevenção por estabelecimento: {}", estabelecimentoId);

        Page<AcaoPromocaoPrevencao> acoes = acaoPromocaoPrevencaoRepository.findByEstabelecimentoIdOrderByDataInicioDesc(estabelecimentoId, pageable);
        return acoes.map(acaoPromocaoPrevencaoMapper::toResponse);
    }

    @Override
    public Page<AcaoPromocaoPrevencaoResponse> listarPorProfissionalResponsavel(UUID profissionalId, Pageable pageable) {
        log.debug("Listando ações de promoção e prevenção por profissional: {}", profissionalId);

        Page<AcaoPromocaoPrevencao> acoes = acaoPromocaoPrevencaoRepository.findByProfissionalResponsavelIdOrderByDataInicioDesc(profissionalId, pageable);
        return acoes.map(acaoPromocaoPrevencaoMapper::toResponse);
    }

    @Override
    public Page<AcaoPromocaoPrevencaoResponse> listarPorStatus(String status, UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando ações de promoção e prevenção por status: {}", status);

        Page<AcaoPromocaoPrevencao> acoes = acaoPromocaoPrevencaoRepository.findByStatusAcaoAndEstabelecimentoIdOrderByDataInicioDesc(status, estabelecimentoId, pageable);
        return acoes.map(acaoPromocaoPrevencaoMapper::toResponse);
    }

    @Override
    public Page<AcaoPromocaoPrevencaoResponse> listarContinuas(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando ações de promoção e prevenção contínuas: {}", estabelecimentoId);

        Page<AcaoPromocaoPrevencao> acoes = acaoPromocaoPrevencaoRepository.findByAcaoContinuaAndEstabelecimentoIdOrderByDataInicioDesc(true, estabelecimentoId, pageable);
        return acoes.map(acaoPromocaoPrevencaoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "acaopromocaoprevencao", key = "#id")
    public AcaoPromocaoPrevencaoResponse atualizar(UUID id, AcaoPromocaoPrevencaoRequest request) {
        log.debug("Atualizando ação de promoção e prevenção. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ação de promoção e prevenção é obrigatório");
        }

        AcaoPromocaoPrevencao acaoExistente = acaoPromocaoPrevencaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ação de promoção e prevenção não encontrada com ID: " + id));

        atualizarDadosAcaoPromocaoPrevencao(acaoExistente, request);

        AcaoPromocaoPrevencao acaoAtualizada = acaoPromocaoPrevencaoRepository.save(acaoExistente);
        log.info("Ação de promoção e prevenção atualizada com sucesso. ID: {}", acaoAtualizada.getId());

        return acaoPromocaoPrevencaoMapper.toResponse(acaoAtualizada);
    }

    @Override
    @Transactional
    @CacheEvict(value = "acaopromocaoprevencao", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo ação de promoção e prevenção. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ação de promoção e prevenção é obrigatório");
        }

        AcaoPromocaoPrevencao acaoPromocaoPrevencao = acaoPromocaoPrevencaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ação de promoção e prevenção não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(acaoPromocaoPrevencao.getActive())) {
            throw new BadRequestException("Ação de promoção e prevenção já está inativa");
        }

        acaoPromocaoPrevencao.setActive(false);
        acaoPromocaoPrevencaoRepository.save(acaoPromocaoPrevencao);
        log.info("Ação de promoção e prevenção excluída (desativada) com sucesso. ID: {}", id);
    }

    private void atualizarDadosAcaoPromocaoPrevencao(AcaoPromocaoPrevencao acao, AcaoPromocaoPrevencaoRequest request) {
        AcaoPromocaoPrevencao acaoAtualizada = acaoPromocaoPrevencaoMapper.fromRequest(request);

        UUID idOriginal = acao.getId();
        com.upsaude.entity.Tenant tenantOriginal = acao.getTenant();
        Boolean activeOriginal = acao.getActive();
        java.time.OffsetDateTime createdAtOriginal = acao.getCreatedAt();

        BeanUtils.copyProperties(acaoAtualizada, acao);

        acao.setId(idOriginal);
        acao.setTenant(tenantOriginal);
        acao.setActive(activeOriginal);
        acao.setCreatedAt(createdAtOriginal);
    }
}
