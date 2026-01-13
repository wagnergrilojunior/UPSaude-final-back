package com.upsaude.service.impl.api.faturamento;

import com.upsaude.api.request.faturamento.GlosaRequest;
import com.upsaude.api.response.faturamento.GlosaResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
import com.upsaude.entity.faturamento.Glosa;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.faturamento.GlosaMapper;
import com.upsaude.repository.faturamento.DocumentoFaturamentoItemRepository;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.faturamento.GlosaRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.faturamento.GlosaService;
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
public class GlosaServiceImpl implements GlosaService {

    private final GlosaRepository repository;
    private final DocumentoFaturamentoRepository documentoRepository;
    private final DocumentoFaturamentoItemRepository itemRepository;
    private final GlosaMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public GlosaResponse criar(GlosaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        DocumentoFaturamento documento = documentoRepository.findByIdAndTenant(request.getDocumento(), tenantId)
                .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + request.getDocumento()));

        Glosa entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setDocumento(documento);

        if (request.getItem() != null) {
            DocumentoFaturamentoItem item = itemRepository.findByIdAndTenant(request.getItem(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Item de documento de faturamento não encontrado com ID: " + request.getItem()));
            entity.setItem(item);
        }

        Glosa saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public GlosaResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        Glosa entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Glosa não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GlosaResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public GlosaResponse atualizar(UUID id, GlosaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Glosa entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Glosa não encontrada com ID: " + id));
        mapper.updateFromRequest(request, entity);
        Glosa saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            Glosa entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Glosa não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir glosa", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            Glosa entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Glosa não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Glosa já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar glosa", e);
        }
    }
}

