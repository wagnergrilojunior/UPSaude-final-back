package com.upsaude.service.api.support.medico;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoRelacionamentosHandler {

    private final SigtapCboRepository cboRepository;
    private final EnderecoRepository enderecoRepository;

    public Medicos processarRelacionamentos(Medicos medico, MedicosRequest request, Tenant tenant) {
        log.debug("Processando relacionamentos do médico");

        processarEndereco(medico, request, tenant);
        processarEstabelecimentos(medico, request, tenant);
        processarEspecialidades(medico, request);

        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
        return medico;
    }

    private void processarEndereco(Medicos medico, MedicosRequest request, Tenant tenant) {
        if (request.getEnderecoMedico() != null) {
            log.debug("Processando endereço médico como UUID: {}", request.getEnderecoMedico());
            Endereco enderecoMedico = enderecoRepository.findById(Objects.requireNonNull(request.getEnderecoMedico()))
                    .orElseThrow(() -> new NotFoundException("Endereço médico não encontrado com ID: " + request.getEnderecoMedico()));
            medico.setEnderecoMedico(enderecoMedico);
        } else {
            log.debug("Endereço médico não fornecido. Mantendo endereço existente.");
        }
    }

    private void processarEstabelecimentos(Medicos medico, MedicosRequest request, Tenant tenant) {
        // Estabelecimentos devem ser processados através do endpoint específico de vínculos
        // (medicosEstabelecimentos) quando enviado separadamente via endpoint específico
        log.debug("Estabelecimentos devem ser processados através do endpoint específico de vínculos para médico ID: {}", medico.getId());
    }

    private void processarEspecialidades(Medicos medico, MedicosRequest request) {
        if (request.getEspecialidades() != null && !request.getEspecialidades().isEmpty()) {
            log.debug("Processando {} especialidade(s) para o médico", request.getEspecialidades().size());

            Set<UUID> especialidadesIdsUnicos = new LinkedHashSet<>(request.getEspecialidades());

            if (especialidadesIdsUnicos.size() != request.getEspecialidades().size()) {
                log.warn("Lista de especialidades contém IDs duplicados. Removendo duplicatas.");
            }

            // Se é uma atualização (médico já tem ID), limpar especialidades existentes que não estão na lista
            // Se é uma criação (médico não tem ID), apenas adicionar as novas
            if (medico.getId() != null) {
                Set<UUID> especialidadesParaManter = new LinkedHashSet<>(especialidadesIdsUnicos);
                medico.getEspecialidades().removeIf(especialidade ->
                    !especialidadesParaManter.contains(especialidade.getId())
                );
            } else {
                // Na criação, limpar lista inicial
                medico.getEspecialidades().clear();
            }

            // Adicionar novas especialidades
            for (UUID especialidadeId : especialidadesIdsUnicos) {
                if (especialidadeId == null) {
                    log.warn("ID de especialidade nulo encontrado na lista. Ignorando.");
                    continue;
                }

                // Verificar se já está associada
                boolean jaExiste = medico.getEspecialidades().stream()
                        .anyMatch(esp -> esp.getId().equals(especialidadeId));

                if (!jaExiste) {
                    SigtapOcupacao especialidade = cboRepository.findById(especialidadeId)
                            .orElseThrow(() -> new NotFoundException(
                                    "Especialidade (CBO) não encontrada com ID: " + especialidadeId));

                    medico.addEspecialidade(especialidade);
                    log.debug("Especialidade {} adicionada ao médico", especialidadeId);
                }
            }

            log.debug("{} especialidade(s) vinculada(s) ao médico com sucesso", medico.getEspecialidades().size());
        } else {
            // Se lista vazia ou null na criação, não adiciona especialidades
            // Se for atualização, mantém as existentes (não limpa)
            if (medico.getId() == null) {
                medico.getEspecialidades().clear();
                log.debug("Nenhuma especialidade fornecida no cadastro inicial. Lista de especialidades será vazia.");
            } else {
                log.debug("Nenhuma especialidade fornecida no request de atualização. Mantendo especialidades existentes.");
            }
        }
    }
}
