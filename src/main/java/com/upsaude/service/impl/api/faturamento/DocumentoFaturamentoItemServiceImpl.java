package com.upsaude.service.impl.api.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoItemRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoItemResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.faturamento.DocumentoFaturamentoItemMapper;
import com.upsaude.repository.faturamento.DocumentoFaturamentoItemRepository;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.faturamento.DocumentoFaturamentoItemService;
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
public class DocumentoFaturamentoItemServiceImpl implements DocumentoFaturamentoItemService {

    private final DocumentoFaturamentoItemRepository repository;
    private final DocumentoFaturamentoRepository documentoRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;
    private final DocumentoFaturamentoItemMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public DocumentoFaturamentoItemResponse criar(UUID documentoId, DocumentoFaturamentoItemRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        DocumentoFaturamento documento = documentoRepository.findByIdAndTenant(documentoId, tenantId)
                .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + documentoId));

        DocumentoFaturamentoItem entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setDocumento(documento);

        if (request.getSigtapProcedimento() != null) {
            SigtapProcedimento proc = sigtapProcedimentoRepository.findById(request.getSigtapProcedimento())
                    .orElseThrow(() -> new BadRequestException("Procedimento SIGTAP não encontrado com ID: " + request.getSigtapProcedimento()));
            entity.setSigtapProcedimento(proc);
        }

        DocumentoFaturamentoItem saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoFaturamentoItemResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        DocumentoFaturamentoItem entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Item de documento de faturamento não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoFaturamentoItemResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public DocumentoFaturamentoItemResponse atualizar(UUID id, DocumentoFaturamentoItemRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        DocumentoFaturamentoItem entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Item de documento de faturamento não encontrado com ID: " + id));
        mapper.updateFromRequest(request, entity);
        if (request.getSigtapProcedimento() != null) {
            SigtapProcedimento proc = sigtapProcedimentoRepository.findById(request.getSigtapProcedimento())
                    .orElseThrow(() -> new BadRequestException("Procedimento SIGTAP não encontrado com ID: " + request.getSigtapProcedimento()));
            entity.setSigtapProcedimento(proc);
        } else {
            entity.setSigtapProcedimento(null);
        }
        DocumentoFaturamentoItem saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            DocumentoFaturamentoItem entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Item de documento de faturamento não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir item de documento de faturamento", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            DocumentoFaturamentoItem entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Item de documento de faturamento não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Item de documento de faturamento já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar item de documento de faturamento", e);
        }
    }
}

