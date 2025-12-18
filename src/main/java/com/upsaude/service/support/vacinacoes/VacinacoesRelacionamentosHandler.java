package com.upsaude.service.support.vacinacoes;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.vacina.VacinacoesRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.saude_publica.vacina.FabricantesVacina;
import com.upsaude.entity.saude_publica.vacina.Vacinacoes;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.vacina.FabricantesVacinaRepository;
import com.upsaude.repository.saude_publica.vacina.VacinasRepository;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacinacoesRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final VacinasRepository vacinasRepository;
    private final FabricantesVacinaRepository fabricantesVacinaRepository;

    public void resolver(Vacinacoes entity, VacinacoesRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        UUID vacinaId = Objects.requireNonNull(request.getVacina(), "vacina é obrigatória");
        Vacinas vacina = vacinasRepository.findById(vacinaId)
            .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + vacinaId));
        entity.setVacina(vacina);

        if (request.getFabricante() != null) {
            UUID fabricanteId = Objects.requireNonNull(request.getFabricante(), "fabricante");
            FabricantesVacina fabricante = fabricantesVacinaRepository.findById(fabricanteId)
                .orElseThrow(() -> new NotFoundException("Fabricante de vacina não encontrado com ID: " + fabricanteId));
            entity.setFabricante(fabricante);
        } else {
            entity.setFabricante(null);
        }

        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
        } else {
            entity.setEstabelecimento(null);
        }
    }
}

