package com.upsaude.service.support.atendimento;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.embeddable.AnamneseAtendimentoMapper;
import com.upsaude.mapper.embeddable.ClassificacaoRiscoAtendimentoMapper;
import com.upsaude.mapper.embeddable.DiagnosticoAtendimentoMapper;
import com.upsaude.mapper.embeddable.InformacoesAtendimentoMapper;
import com.upsaude.mapper.embeddable.ProcedimentosRealizadosAtendimentoMapper;
import com.upsaude.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoUpdater {

    private final AtendimentoRepository repository;
    private final AtendimentoTenantEnforcer tenantEnforcer;
    private final AtendimentoValidationService validationService;
    private final AtendimentoRelacionamentosHandler relacionamentosHandler;
    private final AtendimentoDomainService domainService;

    private final InformacoesAtendimentoMapper informacoesAtendimentoMapper;
    private final AnamneseAtendimentoMapper anamneseAtendimentoMapper;
    private final DiagnosticoAtendimentoMapper diagnosticoAtendimentoMapper;
    private final ProcedimentosRealizadosAtendimentoMapper procedimentosRealizadosAtendimentoMapper;
    private final ClassificacaoRiscoAtendimentoMapper classificacaoRiscoAtendimentoMapper;

    public Atendimento atualizar(UUID id, AtendimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        atualizarCampos(entity, request);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Atendimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Atendimento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }

    private void atualizarCampos(Atendimento atendimento, AtendimentoRequest request) {

        if (request.getInformacoes() != null) {
            if (atendimento.getInformacoes() == null) {
                atendimento.setInformacoes(informacoesAtendimentoMapper.toEntity(request.getInformacoes()));
            } else {
                informacoesAtendimentoMapper.updateFromRequest(request.getInformacoes(), atendimento.getInformacoes());
            }
        }

        if (request.getAnamnese() != null) {
            if (atendimento.getAnamnese() == null) {
                atendimento.setAnamnese(anamneseAtendimentoMapper.toEntity(request.getAnamnese()));
            } else {
                anamneseAtendimentoMapper.updateFromRequest(request.getAnamnese(), atendimento.getAnamnese());
            }
        }

        if (request.getDiagnostico() != null) {
            if (atendimento.getDiagnostico() == null) {
                atendimento.setDiagnostico(diagnosticoAtendimentoMapper.toEntity(request.getDiagnostico()));
            } else {
                diagnosticoAtendimentoMapper.updateFromRequest(request.getDiagnostico(), atendimento.getDiagnostico());
            }
        }

        if (request.getProcedimentosRealizados() != null) {
            if (atendimento.getProcedimentosRealizados() == null) {
                atendimento.setProcedimentosRealizados(procedimentosRealizadosAtendimentoMapper.toEntity(request.getProcedimentosRealizados()));
            } else {
                procedimentosRealizadosAtendimentoMapper.updateFromRequest(request.getProcedimentosRealizados(), atendimento.getProcedimentosRealizados());
            }
        }

        if (request.getClassificacaoRisco() != null) {
            if (atendimento.getClassificacaoRisco() == null) {
                atendimento.setClassificacaoRisco(classificacaoRiscoAtendimentoMapper.toEntity(request.getClassificacaoRisco()));
            } else {
                classificacaoRiscoAtendimentoMapper.updateFromRequest(request.getClassificacaoRisco(), atendimento.getClassificacaoRisco());
            }
        }

        if (request.getAnotacoes() != null) {
            atendimento.setAnotacoes(request.getAnotacoes());
        }

        if (request.getObservacoesInternas() != null) {
            atendimento.setObservacoesInternas(request.getObservacoesInternas());
        }
    }
}
