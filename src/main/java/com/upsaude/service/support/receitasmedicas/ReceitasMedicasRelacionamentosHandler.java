package com.upsaude.service.support.receitasmedicas;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.MedicacaoRepository;
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
    private final CidDoencasRepository cidDoencasRepository;
    private final MedicacaoRepository medicacaoRepository;

    public void resolver(ReceitasMedicas entity, ReceitasMedicasRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);

        Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId);
        entity.setMedico(medico);

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        if (request.getCidPrincipal() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
            entity.setCidPrincipal(cidPrincipal);
        } else {
            entity.setCidPrincipal(null);
        }

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

