package com.upsaude.service.support.dadosclinicosbasicos;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.paciente.DadosClinicosBasicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosValidationService {

    private final DadosClinicosBasicosRepository repository;

    public void validarObrigatorios(DadosClinicosBasicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados clínicos básicos são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
    }

    public void validarDuplicidadeParaCriacao(UUID pacienteId, UUID tenantId) {
        if (repository.existsByPacienteIdAndTenantId(pacienteId, tenantId)) {
            throw new ConflictException("Dados clínicos básicos já existem para este paciente");
        }
    }

    public void validarDuplicidadeParaAtualizacao(UUID id, UUID pacienteId, UUID tenantId) {
        repository.findByPacienteIdAndTenantId(pacienteId, tenantId).ifPresent(d -> {
            if (!d.getId().equals(id)) {
                throw new ConflictException("Dados clínicos básicos já existem para este paciente");
            }
        });
    }
}

