package com.upsaude.service.impl;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.DispensacoesMedicamentosResponse;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DispensacoesMedicamentosMapper;
import com.upsaude.repository.DispensacoesMedicamentosRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.DispensacoesMedicamentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosServiceImpl implements DispensacoesMedicamentosService {

    private final DispensacoesMedicamentosRepository dispensacoesMedicamentosRepository;
    private final DispensacoesMedicamentosMapper dispensacoesMedicamentosMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicacaoRepository medicacaoRepository;

    @Override
    @Transactional
    @CacheEvict(value = "dispensacoesmedicamentos", allEntries = true)
    public DispensacoesMedicamentosResponse criar(DispensacoesMedicamentosRequest request) {
        log.debug("Criando novo dispensacoesmedicamentos");

        DispensacoesMedicamentos dispensacoesMedicamentos = dispensacoesMedicamentosMapper.fromRequest(request);

        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        dispensacoesMedicamentos.setPaciente(paciente);

        Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + request.getMedicacao()));
        dispensacoesMedicamentos.setMedicacao(medicacao);

        dispensacoesMedicamentos.setActive(true);

        DispensacoesMedicamentos dispensacoesMedicamentosSalvo = dispensacoesMedicamentosRepository.save(dispensacoesMedicamentos);
        log.info("DispensacoesMedicamentos criado com sucesso. ID: {}", dispensacoesMedicamentosSalvo.getId());

        return dispensacoesMedicamentosMapper.toResponse(dispensacoesMedicamentosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "dispensacoesmedicamentos", key = "#id")
    public DispensacoesMedicamentosResponse obterPorId(UUID id) {
        log.debug("Buscando dispensacoesmedicamentos por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do dispensacoesmedicamentos é obrigatório");
        }

        DispensacoesMedicamentos dispensacoesMedicamentos = dispensacoesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DispensacoesMedicamentos não encontrado com ID: " + id));

        return dispensacoesMedicamentosMapper.toResponse(dispensacoesMedicamentos);
    }

    @Override
    public Page<DispensacoesMedicamentosResponse> listar(Pageable pageable) {
        log.debug("Listando DispensacoesMedicamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DispensacoesMedicamentos> dispensacoesMedicamentos = dispensacoesMedicamentosRepository.findAll(pageable);
        return dispensacoesMedicamentos.map(dispensacoesMedicamentosMapper::toResponse);
    }

    @Override
    public Page<DispensacoesMedicamentosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando dispensações do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<DispensacoesMedicamentos> dispensacoes = dispensacoesMedicamentosRepository.findByEstabelecimentoIdOrderByDataDispensacaoDesc(estabelecimentoId, pageable);
        return dispensacoes.map(dispensacoesMedicamentosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "dispensacoesmedicamentos", key = "#id")
    public DispensacoesMedicamentosResponse atualizar(UUID id, DispensacoesMedicamentosRequest request) {
        log.debug("Atualizando dispensacoesmedicamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do dispensacoesmedicamentos é obrigatório");
        }

        DispensacoesMedicamentos dispensacoesMedicamentosExistente = dispensacoesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DispensacoesMedicamentos não encontrado com ID: " + id));

        atualizarDadosDispensacoesMedicamentos(dispensacoesMedicamentosExistente, request);

        DispensacoesMedicamentos dispensacoesMedicamentosAtualizado = dispensacoesMedicamentosRepository.save(dispensacoesMedicamentosExistente);
        log.info("DispensacoesMedicamentos atualizado com sucesso. ID: {}", dispensacoesMedicamentosAtualizado.getId());

        return dispensacoesMedicamentosMapper.toResponse(dispensacoesMedicamentosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "dispensacoesmedicamentos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo dispensacoesmedicamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do dispensacoesmedicamentos é obrigatório");
        }

        DispensacoesMedicamentos dispensacoesMedicamentos = dispensacoesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DispensacoesMedicamentos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(dispensacoesMedicamentos.getActive())) {
            throw new BadRequestException("DispensacoesMedicamentos já está inativo");
        }

        dispensacoesMedicamentos.setActive(false);
        dispensacoesMedicamentosRepository.save(dispensacoesMedicamentos);
        log.info("DispensacoesMedicamentos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void atualizarDadosDispensacoesMedicamentos(DispensacoesMedicamentos dispensacoesMedicamentos, DispensacoesMedicamentosRequest request) {

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            dispensacoesMedicamentos.setPaciente(paciente);
        }

        if (request.getMedicacao() != null) {
            Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                    .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + request.getMedicacao()));
            dispensacoesMedicamentos.setMedicacao(medicacao);
        }

        if (request.getQuantidade() != null) {
            dispensacoesMedicamentos.setQuantidade(request.getQuantidade());
        }
        if (request.getDataDispensacao() != null) {
            dispensacoesMedicamentos.setDataDispensacao(request.getDataDispensacao());
        }
        if (request.getObservacoes() != null) {
            dispensacoesMedicamentos.setObservacoes(request.getObservacoes());
        }
    }
}
