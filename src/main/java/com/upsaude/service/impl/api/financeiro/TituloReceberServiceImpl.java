package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.TituloReceberRequest;
import com.upsaude.api.response.financeiro.TituloReceberResponse;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.financeiro.ParteFinanceira;
import com.upsaude.entity.financeiro.TituloReceber;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.TituloReceberMapper;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.financeiro.ParteFinanceiraRepository;
import com.upsaude.repository.financeiro.TituloReceberRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.TituloReceberService;
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
public class TituloReceberServiceImpl implements TituloReceberService {

    private final TituloReceberRepository repository;
    private final DocumentoFaturamentoRepository documentoRepository;
    private final ParteFinanceiraRepository parteRepository;
    private final ContaContabilRepository contaContabilRepository;
    private final CentroCustoRepository centroCustoRepository;
    private final TituloReceberMapper mapper;

    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public TituloReceberResponse criar(TituloReceberRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        TituloReceber entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        if (entity.getIdempotencyKey() == null) {
            entity.setIdempotencyKey(UUID.randomUUID().toString());
        }

        if (request.getDocumentoFaturamento() != null) {
            DocumentoFaturamento doc = documentoRepository.findByIdAndTenant(request.getDocumentoFaturamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + request.getDocumentoFaturamento()));
            entity.setDocumentoFaturamento(doc);
        }

        ParteFinanceira pagador = parteRepository.findByIdAndTenant(request.getPagador(), tenantId)
                .orElseThrow(() -> new BadRequestException("Pagador não encontrado com ID: " + request.getPagador()));
        entity.setPagador(pagador);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabilReceita(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + request.getContaContabilReceita()));
        entity.setContaContabilReceita(conta);

        if (request.getCentroCusto() != null) {
            CentroCusto cc = centroCustoRepository.findByIdAndTenant(request.getCentroCusto(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + request.getCentroCusto()));
            entity.setCentroCusto(cc);
        }

        TituloReceber saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TituloReceberResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        TituloReceber entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Título a receber não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TituloReceberResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public TituloReceberResponse atualizar(UUID id, TituloReceberRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        TituloReceber entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Título a receber não encontrado com ID: " + id));

        mapper.updateFromRequest(request, entity);

        if (request.getDocumentoFaturamento() != null) {
            DocumentoFaturamento doc = documentoRepository.findByIdAndTenant(request.getDocumentoFaturamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + request.getDocumentoFaturamento()));
            entity.setDocumentoFaturamento(doc);
        } else {
            entity.setDocumentoFaturamento(null);
        }

        ParteFinanceira pagador = parteRepository.findByIdAndTenant(request.getPagador(), tenantId)
                .orElseThrow(() -> new BadRequestException("Pagador não encontrado com ID: " + request.getPagador()));
        entity.setPagador(pagador);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabilReceita(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + request.getContaContabilReceita()));
        entity.setContaContabilReceita(conta);

        if (request.getCentroCusto() != null) {
            CentroCusto cc = centroCustoRepository.findByIdAndTenant(request.getCentroCusto(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + request.getCentroCusto()));
            entity.setCentroCusto(cc);
        } else {
            entity.setCentroCusto(null);
        }

        TituloReceber saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            TituloReceber entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Título a receber não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir título a receber", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            TituloReceber entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Título a receber não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Título a receber já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar título a receber", e);
        }
    }
}

