package com.upsaude.service.api.support.dadosclinicosbasicos;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public DadosClinicosBasicos processarRelacionamentos(DadosClinicosBasicos entity, DadosClinicosBasicosRequest request, UUID tenantId) {
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);
        return entity;
    }
}

