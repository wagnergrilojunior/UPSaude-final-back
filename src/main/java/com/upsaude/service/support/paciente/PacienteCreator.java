package com.upsaude.service.support.paciente;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.repository.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteCreator {

    private final PacienteValidationService validationService;
    private final PacienteMapper pacienteMapper;
    private final PacienteOneToOneRelationshipHandler oneToOneHandler;
    private final PacienteRepository pacienteRepository;

    public Paciente criar(PacienteRequest request, UUID tenantId) {
        log.debug("Criando novo paciente: {}", request != null ? request.getNomeCompleto() : "null");

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, pacienteRepository, tenantId);
        validationService.sanitizarFlags(request);

        Paciente paciente = pacienteMapper.fromRequest(request);
        paciente.setActive(true);
        if (paciente.getStatusPaciente() == null) {
            paciente.setStatusPaciente(StatusPacienteEnum.ATIVO);
        }

        oneToOneHandler.processarRelacionamentos(paciente, request);

        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        log.info("Paciente criado com sucesso. ID: {}, Tenant: {}", pacienteSalvo.getId(), tenantId);

        return pacienteSalvo;
    }
}
