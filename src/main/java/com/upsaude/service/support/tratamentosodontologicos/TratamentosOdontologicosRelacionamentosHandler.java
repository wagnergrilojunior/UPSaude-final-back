package com.upsaude.service.support.tratamentosodontologicos;

import com.upsaude.api.request.odontologia.TratamentosOdontologicosRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(TratamentosOdontologicos entity, TratamentosOdontologicosRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
        entity.setProfissional(profissional);

        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
            return;
        }

        if (profissional.getEstabelecimento() != null) {
            entity.setEstabelecimento(profissional.getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}

