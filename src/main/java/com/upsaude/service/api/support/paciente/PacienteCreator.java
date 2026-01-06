package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.paciente.PacienteIdentificadorRepository;
import com.upsaude.repository.paciente.PacienteContatoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteCreator {

    private final PacienteValidationService validationService;
    private final PacienteMapper pacienteMapper;
    private final PacienteAssociacoesManager associacoesManager;
    private final PacienteRepository pacienteRepository;
    private final PacienteIdentificadorRepository identificadorRepository;
    private final PacienteContatoRepository contatoRepository;

    public Paciente criar(PacienteRequest request, UUID tenantId) {
        log.debug("Criando novo paciente: {}", request != null ? request.getNomeCompleto() : "null");

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, pacienteRepository, identificadorRepository,
                contatoRepository, tenantId);
        validationService.sanitizarFlags(request);

        Paciente paciente = pacienteMapper.fromRequest(request);
        paciente.setActive(true);
        if (paciente.getStatusPaciente() == null) {
            paciente.setStatusPaciente(StatusPacienteEnum.ATIVO);
        }

        // Processar todas as associações (usando cascades para salvar)
        associacoesManager.processarTodas(paciente, request, tenantId);

        // Salvar paciente e todas as associações em cascata
        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        log.info("Paciente criado com sucesso. ID: {}, Tenant: {}", pacienteSalvo.getId(), tenantId);

        return pacienteSalvo;
    }
}
