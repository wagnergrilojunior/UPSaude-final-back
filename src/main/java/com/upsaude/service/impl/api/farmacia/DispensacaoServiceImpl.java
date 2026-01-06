package com.upsaude.service.impl.api.farmacia;

import com.upsaude.api.request.farmacia.DispensacaoRequest;
import com.upsaude.api.response.farmacia.DispensacaoResponse;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.farmacia.ReceitaMapper;
import com.upsaude.repository.farmacia.DispensacaoRepository;
import com.upsaude.service.api.farmacia.DispensacaoService;
import com.upsaude.service.api.support.farmacia.DispensacaoCreator;
import com.upsaude.service.api.support.farmacia.DispensacaoResponseBuilder;
import com.upsaude.service.api.support.farmacia.DispensacaoTenantEnforcer;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacaoServiceImpl implements DispensacaoService {

    private final DispensacaoRepository dispensacaoRepository;
    private final TenantService tenantService;
    private final DispensacaoCreator dispensacaoCreator;
    private final DispensacaoResponseBuilder responseBuilder;
    private final DispensacaoTenantEnforcer tenantEnforcer;
    private final ReceitaMapper receitaMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ReceitaResponse> listarReceitasPendentes(UUID farmaciaId, Pageable pageable) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            tenantEnforcer.validarFarmacia(farmaciaId, tenantId);
            Page<Receita> receitas = dispensacaoRepository.findReceitasPendentes(tenantId, pageable);
            return receitas.map(receitaMapper::toResponseCompleto);
        } catch (NotFoundException e) {
            log.warn("Erro ao listar receitas pendentes. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar receitas pendentes. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar receitas pendentes", e);
        }
    }

    @Override
    @Transactional
    public DispensacaoResponse registrarDispensacao(UUID farmaciaId, DispensacaoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Dispensacao dispensacao = dispensacaoCreator.criar(request, farmaciaId, tenantId);
            Dispensacao dispensacaoSalva = dispensacaoRepository.save(dispensacao);
            return responseBuilder.build(dispensacaoSalva);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Dispensação. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Dispensação. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Dispensação", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispensacaoResponse> listarDispensacoes(UUID farmaciaId, Pageable pageable) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            tenantEnforcer.validarFarmacia(farmaciaId, tenantId);
            Page<Dispensacao> dispensacoes = dispensacaoRepository.findByFarmaciaId(farmaciaId, pageable);
            return dispensacoes.map(responseBuilder::build);
        } catch (NotFoundException e) {
            log.warn("Erro ao listar dispensações. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar dispensações. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar dispensações", e);
        }
    }
}
