package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.entity.medicacao.DispensacoesMedicamentos;
import com.upsaude.entity.medicacao.Medicacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.medicacao.MedicacaoRepository;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final MedicacaoRepository medicacaoRepository;

    public void resolver(DispensacoesMedicamentos entity, DispensacoesMedicamentosRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getPaciente(), "paciente é obrigatório"), tenantId));

        UUID medicacaoId = Objects.requireNonNull(request.getMedicacao(), "medicação é obrigatória");
        Medicacao medicacao = medicacaoRepository.findById(medicacaoId)
            .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + medicacaoId));
        entity.setMedicacao(medicacao);

        // Não é possível inferir estabelecimento com segurança a partir do Paciente (não há getEstabelecimento()).
        entity.setEstabelecimento(null);
    }
}
