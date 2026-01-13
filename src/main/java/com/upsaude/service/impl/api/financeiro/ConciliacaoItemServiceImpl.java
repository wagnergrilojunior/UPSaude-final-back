package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoItemRequest;
import com.upsaude.api.response.financeiro.ConciliacaoItemResponse;
import com.upsaude.entity.financeiro.ConciliacaoBancaria;
import com.upsaude.entity.financeiro.ConciliacaoItem;
import com.upsaude.entity.financeiro.ExtratoBancarioImportado;
import com.upsaude.entity.financeiro.MovimentacaoConta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.ConciliacaoItemMapper;
import com.upsaude.repository.financeiro.ConciliacaoBancariaRepository;
import com.upsaude.repository.financeiro.ConciliacaoItemRepository;
import com.upsaude.repository.financeiro.ExtratoBancarioImportadoRepository;
import com.upsaude.repository.financeiro.MovimentacaoContaRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.ConciliacaoItemService;
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
public class ConciliacaoItemServiceImpl implements ConciliacaoItemService {

    private final ConciliacaoItemRepository repository;
    private final ConciliacaoBancariaRepository conciliacaoRepository;
    private final ExtratoBancarioImportadoRepository extratoRepository;
    private final MovimentacaoContaRepository movimentacaoRepository;
    private final ConciliacaoItemMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public ConciliacaoItemResponse criar(ConciliacaoItemRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        ConciliacaoBancaria conciliacao = conciliacaoRepository.findByIdAndTenant(request.getConciliacao(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conciliação bancária não encontrada com ID: " + request.getConciliacao()));

        ConciliacaoItem entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setConciliacao(conciliacao);

        if (request.getExtratoImportado() != null) {
            ExtratoBancarioImportado extrato = extratoRepository.findByIdAndTenant(request.getExtratoImportado(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Extrato importado não encontrado com ID: " + request.getExtratoImportado()));
            entity.setExtratoImportado(extrato);
        }
        if (request.getMovimentacaoConta() != null) {
            MovimentacaoConta mov = movimentacaoRepository.findByIdAndTenant(request.getMovimentacaoConta(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Movimentação não encontrada com ID: " + request.getMovimentacaoConta()));
            entity.setMovimentacaoConta(mov);
        }

        ConciliacaoItem saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ConciliacaoItemResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ConciliacaoItem entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Item de conciliação não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConciliacaoItemResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public ConciliacaoItemResponse atualizar(UUID id, ConciliacaoItemRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        ConciliacaoItem entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Item de conciliação não encontrado com ID: " + id));
        mapper.updateFromRequest(request, entity);
        ConciliacaoItem saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ConciliacaoItem entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Item de conciliação não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir item de conciliação", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ConciliacaoItem entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Item de conciliação não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Item de conciliação já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar item de conciliação", e);
        }
    }
}

