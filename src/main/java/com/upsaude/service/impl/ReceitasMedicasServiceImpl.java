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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    @CacheEvict(value = "receitasmedicas", allEntries = true)
    public ReceitasMedicasResponse criar(ReceitasMedicasRequest request) {
        log.debug("Criando novo receitasmedicas");

        ReceitasMedicas receitasMedicas = receitasMedicasMapper.fromRequest(request);

        Medicos medico = medicosRepository.findById(request.getMedico())
                .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
        receitasMedicas.setMedico(medico);

        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        receitasMedicas.setPaciente(paciente);

        if (request.getCidPrincipal() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
            receitasMedicas.setCidPrincipal(cidPrincipal);
        }

        receitasMedicas.setActive(true);

        ReceitasMedicas receitasMedicasSalvo = receitasMedicasRepository.save(receitasMedicas);
        log.info("ReceitasMedicas criado com sucesso. ID: {}", receitasMedicasSalvo.getId());

        return receitasMedicasMapper.toResponse(receitasMedicasSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "receitasmedicas", key = "#id")
    public ReceitasMedicasResponse obterPorId(UUID id) {
        log.debug("Buscando receitasmedicas por ID: {} (cache miss)", id);
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
    @CacheEvict(value = "receitasmedicas", key = "#id")
    public ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request) {
        log.debug("Atualizando receitasmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        ReceitasMedicas receitasMedicasExistente = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        atualizarDadosReceitasMedicas(receitasMedicasExistente, request);

        ReceitasMedicas receitasMedicasAtualizado = receitasMedicasRepository.save(receitasMedicasExistente);
        log.info("ReceitasMedicas atualizado com sucesso. ID: {}", receitasMedicasAtualizado.getId());

        return receitasMedicasMapper.toResponse(receitasMedicasAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "receitasmedicas", key = "#id")
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

    private void atualizarDadosReceitasMedicas(ReceitasMedicas receitasMedicas, ReceitasMedicasRequest request) {

        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findById(request.getMedico())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            receitasMedicas.setMedico(medico);
        }

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            receitasMedicas.setPaciente(paciente);
        }

        if (request.getCidPrincipal() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
            receitasMedicas.setCidPrincipal(cidPrincipal);
        } else {
            receitasMedicas.setCidPrincipal(null);
        }

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
