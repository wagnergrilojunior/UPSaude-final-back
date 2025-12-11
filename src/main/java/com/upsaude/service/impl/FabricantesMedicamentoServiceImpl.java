package com.upsaude.service.impl;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.api.response.FabricantesMedicamentoResponse;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesMedicamentoMapper;
import com.upsaude.repository.FabricantesMedicamentoRepository;
import com.upsaude.service.FabricantesMedicamentoService;
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
public class FabricantesMedicamentoServiceImpl implements FabricantesMedicamentoService {

    private final FabricantesMedicamentoRepository fabricantesMedicamentoRepository;
    private final FabricantesMedicamentoMapper fabricantesMedicamentoMapper;

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesmedicamento", allEntries = true)
    public FabricantesMedicamentoResponse criar(FabricantesMedicamentoRequest request) {
        log.debug("Criando novo fabricantesmedicamento");

        validarDuplicidade(null, request);

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoMapper.fromRequest(request);
        fabricantesMedicamento.setActive(true);

        FabricantesMedicamento fabricantesMedicamentoSalvo = fabricantesMedicamentoRepository.save(fabricantesMedicamento);
        log.info("FabricantesMedicamento criado com sucesso. ID: {}", fabricantesMedicamentoSalvo.getId());

        return fabricantesMedicamentoMapper.toResponse(fabricantesMedicamentoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "fabricantesmedicamento", key = "#id")
    public FabricantesMedicamentoResponse obterPorId(UUID id) {
        log.debug("Buscando fabricantesmedicamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        return fabricantesMedicamentoMapper.toResponse(fabricantesMedicamento);
    }

    @Override
    public Page<FabricantesMedicamentoResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesMedicamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesMedicamento> fabricantesMedicamentos = fabricantesMedicamentoRepository.findAll(pageable);
        return fabricantesMedicamentos.map(fabricantesMedicamentoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesmedicamento", key = "#id")
    public FabricantesMedicamentoResponse atualizar(UUID id, FabricantesMedicamentoRequest request) {
        log.debug("Atualizando fabricantesmedicamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento fabricantesMedicamentoExistente = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        validarDuplicidade(id, request);

        atualizarDadosFabricantesMedicamento(fabricantesMedicamentoExistente, request);

        FabricantesMedicamento fabricantesMedicamentoAtualizado = fabricantesMedicamentoRepository.save(fabricantesMedicamentoExistente);
        log.info("FabricantesMedicamento atualizado com sucesso. ID: {}", fabricantesMedicamentoAtualizado.getId());

        return fabricantesMedicamentoMapper.toResponse(fabricantesMedicamentoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesmedicamento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo fabricantesmedicamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(fabricantesMedicamento.getActive())) {
            throw new BadRequestException("FabricantesMedicamento já está inativo");
        }

        fabricantesMedicamento.setActive(false);
        fabricantesMedicamentoRepository.save(fabricantesMedicamento);
        log.info("FabricantesMedicamento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDuplicidade(UUID id, FabricantesMedicamentoRequest request) {
        if (request == null) {
            return;
        }

        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {

                nomeDuplicado = fabricantesMedicamentoRepository.existsByNome(request.getNome().trim());
            } else {

                nomeDuplicado = fabricantesMedicamentoRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de medicamento com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe um fabricante de medicamento cadastrado com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }
    }

    private void atualizarDadosFabricantesMedicamento(FabricantesMedicamento fabricantesMedicamento, FabricantesMedicamentoRequest request) {
        FabricantesMedicamento fabricantesMedicamentoAtualizado = fabricantesMedicamentoMapper.fromRequest(request);

        java.util.UUID idOriginal = fabricantesMedicamento.getId();
        Boolean activeOriginal = fabricantesMedicamento.getActive();
        java.time.OffsetDateTime createdAtOriginal = fabricantesMedicamento.getCreatedAt();

        BeanUtils.copyProperties(fabricantesMedicamentoAtualizado, fabricantesMedicamento);

        fabricantesMedicamento.setId(idOriginal);
        fabricantesMedicamento.setActive(activeOriginal);
        fabricantesMedicamento.setCreatedAt(createdAtOriginal);
    }
}
