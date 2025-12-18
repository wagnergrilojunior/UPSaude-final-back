package com.upsaude.service.support.medico;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.EspecialidadesMedicasRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.MedicoEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoRelacionamentosHandler {

    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final MedicoEstabelecimentoRepository medicoEstabelecimentoRepository;

    public Medicos processarRelacionamentos(Medicos medico, MedicosRequest request, Tenant tenant) {
        log.debug("Processando relacionamentos do médico");

        processarEspecialidades(medico, request);
        processarEstabelecimentos(medico, request, tenant);

        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
        return medico;
    }

    private void processarEspecialidades(Medicos medico, MedicosRequest request) {
        if (request.getEspecialidades() != null && !request.getEspecialidades().isEmpty()) {
            log.debug("Processando {} especialidade(s) para o médico", request.getEspecialidades().size());
            List<EspecialidadesMedicas> especialidades = new ArrayList<>();

            Set<UUID> especialidadesIdsUnicos = new LinkedHashSet<>(request.getEspecialidades());

            if (especialidadesIdsUnicos.size() != request.getEspecialidades().size()) {
                log.warn("Lista de especialidades contém IDs duplicados. Removendo duplicatas.");
            }

            for (UUID especialidadeId : especialidadesIdsUnicos) {
                if (especialidadeId == null) {
                    log.warn("ID de especialidade nulo encontrado na lista. Ignorando.");
                    continue;
                }

                EspecialidadesMedicas especialidade = especialidadesMedicasRepository
                        .findById(especialidadeId)
                        .orElseThrow(() -> new NotFoundException(
                                "Especialidade médica não encontrada com ID: " + especialidadeId));
                especialidades.add(especialidade);
                log.debug("Especialidade {} associada ao médico", especialidadeId);
            }

            medico.setEspecialidades(especialidades);
            log.debug("{} especialidade(s) associada(s) ao médico com sucesso", especialidades.size());
        } else {
            medico.setEspecialidades(new ArrayList<>());
            log.debug("Nenhuma especialidade fornecida. Lista de especialidades será limpa.");
        }
    }

    private void processarEstabelecimentos(Medicos medico, MedicosRequest request, Tenant tenant) {
        if (request.getEstabelecimentos() != null && !request.getEstabelecimentos().isEmpty()) {
            log.debug("Processando {} estabelecimento(s) para o médico", request.getEstabelecimentos().size());

            Set<UUID> estabelecimentosIdsUnicos = new LinkedHashSet<>(request.getEstabelecimentos());

            if (estabelecimentosIdsUnicos.size() != request.getEstabelecimentos().size()) {
                log.warn("Lista de estabelecimentos contém IDs duplicados. Removendo duplicatas.");
            }

            Objects.requireNonNull(tenant, "tenant é obrigatório para processar vínculos com estabelecimentos");

            Set<UUID> estabelecimentosParaManter = new LinkedHashSet<>(estabelecimentosIdsUnicos);

            medico.getMedicosEstabelecimentos().removeIf(vinculo ->
                !estabelecimentosParaManter.contains(vinculo.getEstabelecimento().getId())
            );

            for (UUID estabelecimentoId : estabelecimentosIdsUnicos) {
                if (estabelecimentoId == null) {
                    log.warn("ID de estabelecimento nulo encontrado na lista. Ignorando.");
                    continue;
                }

                Estabelecimentos estabelecimento = estabelecimentosRepository
                        .findById(estabelecimentoId)
                        .orElseThrow(() -> new NotFoundException(
                                "Estabelecimento não encontrado com ID: " + estabelecimentoId));

                MedicoEstabelecimento medicoEstabelecimento = null;

                for (MedicoEstabelecimento vinculoExistente : medico.getMedicosEstabelecimentos()) {
                    if (vinculoExistente.getEstabelecimento().getId().equals(estabelecimentoId)) {
                        medicoEstabelecimento = vinculoExistente;
                        log.debug("Vínculo existente encontrado para estabelecimento {}", estabelecimentoId);
                        break;
                    }
                }

                if (medicoEstabelecimento == null && medico.getId() != null) {
                    Optional<MedicoEstabelecimento> vinculoBanco = medicoEstabelecimentoRepository
                            .findByMedicoIdAndEstabelecimentoId(medico.getId(), estabelecimentoId);

                    if (vinculoBanco.isPresent()) {
                        medicoEstabelecimento = vinculoBanco.get();

                        if (!medico.getMedicosEstabelecimentos().contains(medicoEstabelecimento)) {
                            medico.getMedicosEstabelecimentos().add(medicoEstabelecimento);
                        }
                        log.debug("Vínculo encontrado no banco para estabelecimento {}", estabelecimentoId);
                    }
                }

                if (medicoEstabelecimento == null) {
                    medicoEstabelecimento = new MedicoEstabelecimento();
                    medicoEstabelecimento.setMedico(medico);
                    medicoEstabelecimento.setEstabelecimento(estabelecimento);
                    medicoEstabelecimento.setTenant(tenant);
                    medicoEstabelecimento.setActive(true);
                    medicoEstabelecimento.setDataInicio(OffsetDateTime.now());
                    medicoEstabelecimento.setTipoVinculo(TipoVinculoProfissionalEnum.CONTRATO);

                    medico.getMedicosEstabelecimentos().add(medicoEstabelecimento);
                    log.debug("Novo vínculo criado para estabelecimento {}", estabelecimentoId);
                } else {
                    medicoEstabelecimento.setActive(true);
                    if (medicoEstabelecimento.getDataFim() != null) {
                        medicoEstabelecimento.setDataFim(null);
                        log.debug("Vínculo reativado para estabelecimento {}", estabelecimentoId);
                    }
                }
            }

            log.debug("{} estabelecimento(s) vinculado(s) ao médico com sucesso", medico.getMedicosEstabelecimentos().size());
        } else {
            medico.getMedicosEstabelecimentos().clear();
            log.debug("Nenhum estabelecimento fornecido. Lista de vínculos será limpa.");
        }
    }
}
