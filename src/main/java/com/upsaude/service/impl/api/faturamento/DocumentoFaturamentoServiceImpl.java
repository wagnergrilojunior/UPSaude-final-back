package com.upsaude.service.impl.api.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoItemRequest;
import com.upsaude.api.request.faturamento.DocumentoFaturamentoRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.faturamento.DocumentoFaturamentoItemMapper;
import com.upsaude.mapper.faturamento.DocumentoFaturamentoMapper;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.repository.faturamento.DocumentoFaturamentoItemRepository;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.faturamento.DocumentoFaturamentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentoFaturamentoServiceImpl implements DocumentoFaturamentoService {

    private final DocumentoFaturamentoRepository repository;
    private final DocumentoFaturamentoItemRepository itemRepository;
    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final ConvenioRepository convenioRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final GuiaAtendimentoAmbulatorialRepository guiaRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;

    private final DocumentoFaturamentoMapper mapper;
    private final DocumentoFaturamentoItemMapper itemMapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public DocumentoFaturamentoResponse criar(DocumentoFaturamentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        DocumentoFaturamento entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(request.getCompetencia())
                .orElseThrow(() -> new BadRequestException("Competência financeira não encontrada com ID: " + request.getCompetencia()));
        entity.setCompetencia(competencia);

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        }
        if (request.getAgendamento() != null) {
            Agendamento ag = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(ag);
        }
        if (request.getAtendimento() != null) {
            Atendimento at = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(at);
        }
        if (request.getGuiaAmbulatorial() != null) {
            GuiaAtendimentoAmbulatorial guia = guiaRepository.findByIdAndTenant(request.getGuiaAmbulatorial(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Guia ambulatorial não encontrada com ID: " + request.getGuiaAmbulatorial()));
            entity.setGuiaAmbulatorial(guia);
        }

        if (entity.getEmitidoEm() == null) {
            entity.setEmitidoEm(OffsetDateTime.now());
        }

        DocumentoFaturamento saved = repository.save(entity);

        List<DocumentoFaturamentoItem> itens = new ArrayList<>();
        if (request.getItens() != null) {
            for (DocumentoFaturamentoItemRequest itemReq : request.getItens()) {
                DocumentoFaturamentoItem item = itemMapper.fromRequest(itemReq);
                item.setActive(true);
                item.setTenant(tenant);
                item.setDocumento(saved);
                if (itemReq.getSigtapProcedimento() != null) {
                    SigtapProcedimento proc = sigtapProcedimentoRepository.findById(itemReq.getSigtapProcedimento())
                            .orElseThrow(() -> new BadRequestException("Procedimento SIGTAP não encontrado com ID: " + itemReq.getSigtapProcedimento()));
                    item.setSigtapProcedimento(proc);
                }
                itens.add(item);
            }
        }
        if (!itens.isEmpty()) {
            itemRepository.saveAll(itens);
            saved.getItens().clear();
            saved.getItens().addAll(itens);
        }

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoFaturamentoResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        DocumentoFaturamento entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Documento de faturamento não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoFaturamentoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public DocumentoFaturamentoResponse atualizar(UUID id, DocumentoFaturamentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        DocumentoFaturamento entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Documento de faturamento não encontrado com ID: " + id));

        mapper.updateFromRequest(request, entity);

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(request.getCompetencia())
                .orElseThrow(() -> new BadRequestException("Competência financeira não encontrada com ID: " + request.getCompetencia()));
        entity.setCompetencia(competencia);

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        } else {
            entity.setConvenio(null);
        }

        if (request.getAgendamento() != null) {
            Agendamento ag = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(ag);
        } else {
            entity.setAgendamento(null);
        }

        if (request.getAtendimento() != null) {
            Atendimento at = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(at);
        } else {
            entity.setAtendimento(null);
        }

        if (request.getGuiaAmbulatorial() != null) {
            GuiaAtendimentoAmbulatorial guia = guiaRepository.findByIdAndTenant(request.getGuiaAmbulatorial(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Guia ambulatorial não encontrada com ID: " + request.getGuiaAmbulatorial()));
            entity.setGuiaAmbulatorial(guia);
        } else {
            entity.setGuiaAmbulatorial(null);
        }

        DocumentoFaturamento saved = repository.save(entity);

        if (request.getItens() != null) {
            List<DocumentoFaturamentoItem> existentes = itemRepository.findByDocumentoFaturamento(saved.getId(), tenantId);
            if (existentes != null && !existentes.isEmpty()) {
                itemRepository.deleteAll(existentes);
            }
            List<DocumentoFaturamentoItem> novos = new ArrayList<>();
            for (DocumentoFaturamentoItemRequest itemReq : request.getItens()) {
                DocumentoFaturamentoItem item = itemMapper.fromRequest(itemReq);
                item.setActive(true);
                item.setTenant(tenant);
                item.setDocumento(saved);
                if (itemReq.getSigtapProcedimento() != null) {
                    SigtapProcedimento proc = sigtapProcedimentoRepository.findById(itemReq.getSigtapProcedimento())
                            .orElseThrow(() -> new BadRequestException("Procedimento SIGTAP não encontrado com ID: " + itemReq.getSigtapProcedimento()));
                    item.setSigtapProcedimento(proc);
                }
                novos.add(item);
            }
            if (!novos.isEmpty()) {
                itemRepository.saveAll(novos);
                saved.getItens().clear();
                saved.getItens().addAll(novos);
            } else {
                saved.getItens().clear();
            }
        }

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            DocumentoFaturamento entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Documento de faturamento não encontrado com ID: " + id));
            List<DocumentoFaturamentoItem> itens = itemRepository.findByDocumentoFaturamento(entity.getId(), tenantId);
            if (itens != null && !itens.isEmpty()) {
                itemRepository.deleteAll(itens);
            }
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir documento de faturamento", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            DocumentoFaturamento entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Documento de faturamento não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Documento de faturamento já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar documento de faturamento", e);
        }
    }
}

