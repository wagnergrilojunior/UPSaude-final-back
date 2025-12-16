package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Medicacao;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.service.MedicacaoService;
import com.upsaude.service.support.medicacao.MedicacaoCreator;
import com.upsaude.service.support.medicacao.MedicacaoDomainService;
import com.upsaude.service.support.medicacao.MedicacaoResponseBuilder;
import com.upsaude.service.support.medicacao.MedicacaoUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoServiceImpl implements MedicacaoService {

    private final MedicacaoRepository medicacaoRepository;
    private final CacheManager cacheManager;

    private final MedicacaoCreator creator;
    private final MedicacaoUpdater updater;
    private final MedicacaoDomainService domainService;
    private final MedicacaoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public MedicacaoResponse criar(MedicacaoRequest request) {
        log.debug("Criando nova medicação. Request: {}", request);

        try {
            Medicacao medicacao = creator.criar(request);
            MedicacaoResponse response = responseBuilder.build(medicacao);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MEDICACAO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.medicacao(medicacao.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar medicação. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar medicação. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar medicação. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MEDICACAO, keyGenerator = "medicacaoCacheKeyGenerator")
    public MedicacaoResponse obterPorId(UUID id) {
        log.debug("Buscando medicação por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de medicação");
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        try {
            Medicacao medicacao = medicacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

            log.debug("Medicação encontrada. ID: {}", id);
            return responseBuilder.build(medicacao);
        } catch (NotFoundException e) {
            log.warn("Medicação não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar medicação. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar medicação. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicacaoResponse> listar(Pageable pageable) {
        log.debug("Listando medicações paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Medicacao> medicacoes = medicacaoRepository.findAll(pageable);
            log.debug("Listagem de medicações concluída. Total de elementos: {}", medicacoes.getTotalElements());
            return medicacoes.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar medicações. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar medicações", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar medicações. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MEDICACAO, keyGenerator = "medicacaoCacheKeyGenerator")
    public MedicacaoResponse atualizar(UUID id, MedicacaoRequest request) {
        log.debug("Atualizando medicação. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de medicação");
            throw new BadRequestException("ID da medicação é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de medicação. ID: {}", id);
            throw new BadRequestException("Dados da medicação são obrigatórios");
        }

        try {
            Medicacao medicacaoAtualizado = updater.atualizar(id, request);
            return responseBuilder.build(medicacaoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar medicação não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar medicação. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar medicação. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar medicação. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICACAO, keyGenerator = "medicacaoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo medicação. ID: {}", id);

        try {
            inativarInternal(id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir medicação não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir medicação. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir medicação. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir medicação. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de medicação");
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        Medicacao medicacao = medicacaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

        domainService.validarPodeInativar(medicacao);
        medicacao.setActive(false);
        medicacaoRepository.save(Objects.requireNonNull(medicacao));
        log.info("Medicação excluída (desativada) com sucesso. ID: {}", id);
    }

}
