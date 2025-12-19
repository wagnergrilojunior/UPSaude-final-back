package com.upsaude.service.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.alergia.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.request.deficiencia.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.service.alergia.AlergiasPacienteService;
import com.upsaude.service.deficiencia.DeficienciasPacienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteAssociacoesManager {

    private final PacienteAssociacoesHandler associacoesHandler;
    private final AlergiasPacienteService alergiasPacienteService;
    private final DeficienciasPacienteService deficienciasPacienteService;

    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações N:N para paciente ID: {}", paciente.getId());

        // DoencasPaciente removido - Doencas foi deletada
        processarAlergias(paciente, request, tenantId);
        processarDeficiencias(paciente, request, tenantId);
        // MedicacaoPaciente removido - Medicacao foi deletada

        log.info("Todas as associações N:N processadas para paciente ID: {}", paciente.getId());
    }

    private void processarAlergias(Paciente paciente, PacienteRequest request, UUID tenantId) {
        associacoesHandler.processarAssociacoes(
                request.getAlergias(),
                alergiaId -> AlergiasPacienteSimplificadoRequest.builder()
                        .paciente(paciente.getId())
                        .tenant(tenantId)
                        .alergia(alergiaId)
                        .build(),
                alergiasPacienteService::criarSimplificado,
                "Alergia",
                paciente.getId()
        );
    }

    private void processarDeficiencias(Paciente paciente, PacienteRequest request, UUID tenantId) {
        associacoesHandler.processarAssociacoes(
                request.getDeficiencias(),
                defId -> DeficienciasPacienteSimplificadoRequest.builder()
                        .paciente(paciente.getId())
                        .tenant(tenantId)
                        .deficiencia(defId)
                        .build(),
                deficienciasPacienteService::criarSimplificado,
                "Deficiência",
                paciente.getId()
        );
    }
}
