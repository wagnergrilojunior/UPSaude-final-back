package com.upsaude.service.support.receitasmedicas;

import com.upsaude.api.request.medicacao.ReceitasMedicasRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.medicacao.Medicacao;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.medicacao.ReceitasMedicas;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.medicacao.MedicacaoRepository;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceitasMedicasRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final MedicacaoRepository medicacaoRepository;

    public void resolver(ReceitasMedicas entity, ReceitasMedicasRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);

        Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId);
        entity.setMedico(medico);

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        // CidPrincipal removido - CidDoencas foi deletado

        List<UUID> medicacoesIds = request.getMedicacoes() != null ? request.getMedicacoes() : List.of();
        if (!medicacoesIds.isEmpty()) {
            List<Medicacao> medicacoes = new ArrayList<>();
            for (UUID medicacaoId : medicacoesIds) {
                Medicacao medicacao = medicacaoRepository.findById(medicacaoId)
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + medicacaoId));
                medicacoes.add(medicacao);
            }
            entity.setMedicacoes(medicacoes);
        } else {
            entity.setMedicacoes(new ArrayList<>());
        }

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
    }
}

