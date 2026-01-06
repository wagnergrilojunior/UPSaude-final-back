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
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
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
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoRelacionamentosHandler {

    private final SigtapCboRepository cboRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoService enderecoService;
    private final CidadesRepository cidadesRepository;
    private final EstadosRepository estadosRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;

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
            log.debug("Processando endereço médico como objeto completo");

            var enderecoRequest = request.getEnderecoMedico();
            boolean temCamposPreenchidos = (enderecoRequest.getLogradouro() != null && !enderecoRequest.getLogradouro().trim().isEmpty()) ||
                (enderecoRequest.getCep() != null && !enderecoRequest.getCep().trim().isEmpty()) ||
                (enderecoRequest.getCidade() != null) ||
                (enderecoRequest.getEstado() != null);

            if (!temCamposPreenchidos) {
                log.warn("Endereço médico fornecido mas sem campos preenchidos. Ignorando endereço.");
                medico.setEnderecoMedico(null);
                return;
            }

            Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
            endereco.setActive(true);
            endereco.setTenant(tenant);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            if (enderecoRequest.getEstado() != null) {
                Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                    .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                endereco.setEstado(estado);
            }

            if (enderecoRequest.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                    .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                endereco.setCidade(cidade);
            }

            if ((endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) &&
                (endereco.getCep() == null || endereco.getCep().trim().isEmpty())) {
                log.warn("Endereço sem logradouro nem CEP. Criando novo endereço sem buscar duplicados.");
                Endereco enderecoSalvo = enderecoRepository.save(endereco);
                medico.setEnderecoMedico(enderecoSalvo);
                return;
            }

            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            medico.setEnderecoMedico(enderecoProcessado);
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

            if (medico.getId() != null) {
                Set<UUID> estabelecimentosParaManter = new LinkedHashSet<>(estabelecimentosIdsUnicos);

                medico.getEstabelecimentos().removeIf(medicoEstabelecimento ->
                    !estabelecimentosParaManter.contains(medicoEstabelecimento.getEstabelecimento().getId())
                );
            } else {

                medico.getEstabelecimentos().clear();
            }

            for (UUID estabelecimentoId : estabelecimentosIdsUnicos) {
                if (estabelecimentoId == null) {
                    log.warn("ID de estabelecimento nulo encontrado na lista. Ignorando.");
                    continue;
                }

                Estabelecimentos estabelecimento = estabelecimentosRepository.findByIdAndTenant(
                        estabelecimentoId, 
                        tenant.getId()
                ).orElseThrow(() -> new NotFoundException(
                        "Estabelecimento não encontrado com ID: " + estabelecimentoId + " para o tenant"));

                boolean jaExiste = medico.getEstabelecimentos().stream()
                        .anyMatch(me -> me.getEstabelecimento().getId().equals(estabelecimentoId)
                                && me.getDataFim() == null);

                if (!jaExiste) {
                    MedicoEstabelecimento medicoEstabelecimento = new MedicoEstabelecimento();
                    medicoEstabelecimento.setMedico(medico);
                    medicoEstabelecimento.setEstabelecimento(estabelecimento);
                    medicoEstabelecimento.setTenant(tenant);
                    medicoEstabelecimento.setDataInicio(OffsetDateTime.now());
                    medicoEstabelecimento.setActive(true);
                    medicoEstabelecimento.setTipoVinculo(TipoVinculoProfissionalEnum.EFETIVO); 

                    medico.addMedicoEstabelecimento(medicoEstabelecimento);
                    log.debug("Vínculo com estabelecimento {} criado para o médico", estabelecimentoId);
                } else {
                    log.debug("Vínculo com estabelecimento {} já existe. Mantendo vínculo existente.", estabelecimentoId);
                }
            }

            log.debug("{} estabelecimento(s) vinculado(s) ao médico com sucesso", medico.getEstabelecimentos().size());
        } else {

            if (medico.getId() == null) {
                medico.getEstabelecimentos().clear();
                log.debug("Nenhum estabelecimento fornecido no cadastro inicial. Lista de estabelecimentos será vazia.");
            } else {
                log.debug("Nenhum estabelecimento fornecido no request de atualização. Mantendo estabelecimentos existentes.");
            }
        }
    }

    private void processarEspecialidades(Medicos medico, MedicosRequest request) {
        if (request.getEspecialidades() != null && !request.getEspecialidades().isEmpty()) {
            log.debug("Processando {} especialidade(s) para o médico", request.getEspecialidades().size());

            Set<UUID> especialidadesIdsUnicos = new LinkedHashSet<>(request.getEspecialidades());

            if (especialidadesIdsUnicos.size() != request.getEspecialidades().size()) {
                log.warn("Lista de especialidades contém IDs duplicados. Removendo duplicatas.");
            }

            if (medico.getId() != null) {
                Set<UUID> especialidadesParaManter = new LinkedHashSet<>(especialidadesIdsUnicos);
                medico.getEspecialidades().removeIf(especialidade ->
                    !especialidadesParaManter.contains(especialidade.getId())
                );
            } else {

                medico.getEspecialidades().clear();
            }

            for (UUID especialidadeId : especialidadesIdsUnicos) {
                if (especialidadeId == null) {
                    log.warn("ID de especialidade nulo encontrado na lista. Ignorando.");
                    continue;
                }

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

            if (medico.getId() == null) {
                medico.getEspecialidades().clear();
                log.debug("Nenhuma especialidade fornecida no cadastro inicial. Lista de especialidades será vazia.");
            } else {
                log.debug("Nenhuma especialidade fornecida no request de atualização. Mantendo especialidades existentes.");
            }
        }
    }
}
