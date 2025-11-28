package com.upsaude.service.impl;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ReceitasMedicasMapper;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ReceitasMedicasRepository;
import com.upsaude.service.ReceitasMedicasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de ReceitasMedicas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitasMedicasServiceImpl implements ReceitasMedicasService {

    private final ReceitasMedicasRepository receitasMedicasRepository;
    private final ReceitasMedicasMapper receitasMedicasMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final MedicosRepository medicosRepository;
    private final PacienteRepository pacienteRepository;
    private final CidDoencasRepository cidDoencasRepository;
    private final MedicacaoRepository medicacaoRepository;

    @Override
    @Transactional
    public ReceitasMedicasResponse criar(ReceitasMedicasRequest request) {
        log.debug("Criando novo receitasmedicas");

        validarDadosBasicos(request);

        ReceitasMedicas receitasMedicas = receitasMedicasMapper.fromRequest(request);

        // Carrega e define o estabelecimento
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
        receitasMedicas.setEstabelecimento(estabelecimento);

        // Carrega e define o médico
        Medicos medico = medicosRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedicoId()));
        receitasMedicas.setMedico(medico);

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        receitasMedicas.setPaciente(paciente);

        // CID principal é opcional
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            receitasMedicas.setCidPrincipal(cidPrincipal);
        }

        // Medicamentos são opcionais
        if (request.getMedicacoesIds() != null && !request.getMedicacoesIds().isEmpty()) {
            for (UUID medicacaoId : request.getMedicacoesIds()) {
                Medicacao medicacao = medicacaoRepository.findById(medicacaoId)
                        .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + medicacaoId));
                receitasMedicas.getMedicacoes().add(medicacao);
            }
        }

        receitasMedicas.setActive(true);

        ReceitasMedicas receitasMedicasSalvo = receitasMedicasRepository.save(receitasMedicas);
        log.info("ReceitasMedicas criado com sucesso. ID: {}", receitasMedicasSalvo.getId());

        return receitasMedicasMapper.toResponse(receitasMedicasSalvo);
    }

    @Override
    @Transactional
    public ReceitasMedicasResponse obterPorId(UUID id) {
        log.debug("Buscando receitasmedicas por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        ReceitasMedicas receitasMedicas = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        return receitasMedicasMapper.toResponse(receitasMedicas);
    }

    @Override
    public Page<ReceitasMedicasResponse> listar(Pageable pageable) {
        log.debug("Listando ReceitasMedicas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ReceitasMedicas> receitasMedicas = receitasMedicasRepository.findAll(pageable);
        return receitasMedicas.map(receitasMedicasMapper::toResponse);
    }

    @Override
    public Page<ReceitasMedicasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando receitas do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<ReceitasMedicas> receitas = receitasMedicasRepository.findByEstabelecimentoIdOrderByDataPrescricaoDesc(estabelecimentoId, pageable);
        return receitas.map(receitasMedicasMapper::toResponse);
    }

    @Override
    @Transactional
    public ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request) {
        log.debug("Atualizando receitasmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        validarDadosBasicos(request);

        ReceitasMedicas receitasMedicasExistente = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        atualizarDadosReceitasMedicas(receitasMedicasExistente, request);

        ReceitasMedicas receitasMedicasAtualizado = receitasMedicasRepository.save(receitasMedicasExistente);
        log.info("ReceitasMedicas atualizado com sucesso. ID: {}", receitasMedicasAtualizado.getId());

        return receitasMedicasMapper.toResponse(receitasMedicasAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo receitasmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        ReceitasMedicas receitasMedicas = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(receitasMedicas.getActive())) {
            throw new BadRequestException("ReceitasMedicas já está inativo");
        }

        receitasMedicas.setActive(false);
        receitasMedicasRepository.save(receitasMedicas);
        log.info("ReceitasMedicas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ReceitasMedicasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do receitasmedicas são obrigatórios");
        }
        if (request.getEstabelecimentoId() == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (request.getMedicoId() == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getNumeroReceita() == null || request.getNumeroReceita().trim().isEmpty()) {
            throw new BadRequestException("Número da receita é obrigatório");
        }
        if (request.getDataPrescricao() == null) {
            throw new BadRequestException("Data de prescrição é obrigatória");
        }
        if (request.getDataValidade() == null) {
            throw new BadRequestException("Data de validade é obrigatória");
        }
        if (request.getUsoContinuo() == null) {
            throw new BadRequestException("Indicação de uso contínuo é obrigatória");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status é obrigatório");
        }
    }

        private void atualizarDadosReceitasMedicas(ReceitasMedicas receitasMedicas, ReceitasMedicasRequest request) {
        // Atualiza estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            receitasMedicas.setEstabelecimento(estabelecimento);
        }

        // Atualiza médico se fornecido
        if (request.getMedicoId() != null) {
            Medicos medico = medicosRepository.findById(request.getMedicoId())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedicoId()));
            receitasMedicas.setMedico(medico);
        }

        // Atualiza paciente se fornecido
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            receitasMedicas.setPaciente(paciente);
        }

        // Atualiza CID principal se fornecido
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            receitasMedicas.setCidPrincipal(cidPrincipal);
        } else {
            receitasMedicas.setCidPrincipal(null);
        }

        // Atualiza medicamentos se fornecido
        if (request.getMedicacoesIds() != null) {
            receitasMedicas.getMedicacoes().clear();
            for (UUID medicacaoId : request.getMedicacoesIds()) {
                Medicacao medicacao = medicacaoRepository.findById(medicacaoId)
                        .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + medicacaoId));
                receitasMedicas.getMedicacoes().add(medicacao);
            }
        }

        // Atualiza outros campos
        if (request.getNumeroReceita() != null) {
            receitasMedicas.setNumeroReceita(request.getNumeroReceita());
        }
        if (request.getDataPrescricao() != null) {
            receitasMedicas.setDataPrescricao(request.getDataPrescricao());
        }
        if (request.getDataValidade() != null) {
            receitasMedicas.setDataValidade(request.getDataValidade());
        }
        if (request.getUsoContinuo() != null) {
            receitasMedicas.setUsoContinuo(request.getUsoContinuo());
        }
        if (request.getStatus() != null) {
            receitasMedicas.setStatus(request.getStatus());
        }
        if (request.getObservacoes() != null) {
            receitasMedicas.setObservacoes(request.getObservacoes());
        }
        if (request.getOrigemReceita() != null) {
            receitasMedicas.setOrigemReceita(request.getOrigemReceita());
        }
    }
}
