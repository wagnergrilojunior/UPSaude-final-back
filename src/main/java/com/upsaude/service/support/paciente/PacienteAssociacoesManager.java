package com.upsaude.service.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.alergia.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.request.clinica.doencas.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.request.clinica.medicacao.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.request.deficiencia.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.service.alergia.AlergiasPacienteService;
import com.upsaude.service.clinica.doencas.DoencasPacienteService;
import com.upsaude.service.clinica.medicacao.MedicacaoPacienteService;
import com.upsaude.service.deficiencia.DeficienciasPacienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteAssociacoesManager {

    private final PacienteAssociacoesHandler associacoesHandler;
    private final DoencasPacienteService doencasPacienteService;
    private final AlergiasPacienteService alergiasPacienteService;
    private final DeficienciasPacienteService deficienciasPacienteService;
    private final MedicacaoPacienteService medicacaoPacienteService;

    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações N:N para paciente ID: {}", paciente.getId());

        processarDoencas(paciente, request, tenantId);
        processarAlergias(paciente, request, tenantId);
        processarDeficiencias(paciente, request, tenantId);
        processarMedicacoes(paciente, request, tenantId);

        log.info("Todas as associações N:N processadas para paciente ID: {}", paciente.getId());
    }

    private void processarDoencas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        associacoesHandler.processarAssociacoes(
                request.getDoencas(),
                doencaId -> DoencasPacienteSimplificadoRequest.builder()
                        .paciente(paciente.getId())
                        .tenant(tenantId)
                        .doenca(doencaId)
                        .build(),
                doencasPacienteService::criarSimplificado,
                "Doença",
                paciente.getId()
        );
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

    private void processarMedicacoes(Paciente paciente, PacienteRequest request, UUID tenantId) {
        associacoesHandler.processarAssociacoes(
                request.getMedicacoes(),
                medId -> MedicacaoPacienteSimplificadoRequest.builder()
                        .paciente(paciente.getId())
                        .tenant(tenantId)
                        .medicacao(medId)
                        .build(),
                medicacaoPacienteService::criarSimplificado,
                "Medicação",
                paciente.getId()
        );
    }
}
