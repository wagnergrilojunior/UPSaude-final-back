package com.upsaude.service.api.support.medico;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.MedicoEstabelecimentoRepository;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import com.upsaude.service.api.geral.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoRelacionamentosHandler {

    private final EstabelecimentosRepository estabelecimentosRepository;
    private final MedicoEstabelecimentoRepository medicoEstabelecimentoRepository;
    private final SigtapCboRepository cboRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    public Medicos processarRelacionamentos(Medicos medico, MedicosRequest request, Tenant tenant) {
        log.debug("Processando relacionamentos do médico");

        processarEndereco(medico, request, tenant);
        processarEstabelecimentos(medico, request, tenant);
        processarEspecialidades(medico, request);

        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
        return medico;
    }

    private void processarEndereco(Medicos medico, MedicosRequest request, Tenant tenant) {
        if (request.getEnderecoMedicoCompleto() != null) {
            log.debug("Processando endereço médico como objeto completo. Usando findOrCreate para evitar duplicação");

            Endereco endereco = enderecoMapper.fromRequest(request.getEnderecoMedicoCompleto());
            endereco.setActive(true);

            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
            }
            endereco.setTenant(tenant);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            if (request.getEnderecoMedicoCompleto().getEstado() != null) {
                Estados estado = estadosRepository.findById(Objects.requireNonNull(request.getEnderecoMedicoCompleto().getEstado()))
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEnderecoMedicoCompleto().getEstado()));
                endereco.setEstado(estado);
            }

            if (request.getEnderecoMedicoCompleto().getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(Objects.requireNonNull(request.getEnderecoMedicoCompleto().getCidade()))
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + request.getEnderecoMedicoCompleto().getCidade()));
                endereco.setCidade(cidade);
            }

            UUID idAntes = endereco.getId();
            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            medico.setEnderecoMedico(enderecoProcessado);

            boolean foiCriadoNovo = idAntes == null && enderecoProcessado.getId() != null;
            log.info("Endereço médico processado. ID: {} - {}",
                    enderecoProcessado.getId(),
                    foiCriadoNovo ? "Novo endereço criado" : "Endereço existente reutilizado");

        } else if (request.getEnderecoMedico() != null) {
            log.debug("Processando endereço médico como UUID: {}", request.getEnderecoMedico());
            Endereco enderecoMedico = enderecoRepository.findById(Objects.requireNonNull(request.getEnderecoMedico()))
                    .orElseThrow(() -> new NotFoundException("Endereço médico não encontrado com ID: " + request.getEnderecoMedico()));
            medico.setEnderecoMedico(enderecoMedico);
        } else {
            log.debug("Endereço médico não fornecido. Mantendo endereço existente.");
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
