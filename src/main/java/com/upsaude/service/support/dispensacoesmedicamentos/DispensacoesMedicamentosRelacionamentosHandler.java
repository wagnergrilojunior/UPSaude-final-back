package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.MedicacaoRepository;
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
