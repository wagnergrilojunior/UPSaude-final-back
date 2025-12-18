package com.upsaude.service.support.dadossociodemograficos;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DadosSociodemograficosRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public DadosSociodemograficos processarRelacionamentos(DadosSociodemograficos entity, DadosSociodemograficosRequest request, UUID tenantId) {
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);
        return entity;
    }
}

