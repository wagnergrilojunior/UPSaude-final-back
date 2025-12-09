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

/**
 * Implementação do serviço de gerenciamento de FabricantesMedicamento.
 *
 * @author UPSaúde
 */
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

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request
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

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

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

        // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    /**
     * Valida se já existe um fabricante de medicamento com o mesmo nome no banco de dados.
     * 
     * @param id ID do fabricante de medicamento sendo atualizado (null para criação)
     * @param request dados do fabricante de medicamento sendo cadastrado/atualizado
     * @throws BadRequestException se já existe um fabricante de medicamento com o mesmo nome
     */
    private void validarDuplicidade(UUID id, FabricantesMedicamentoRequest request) {
        if (request == null) {
            return;
        }

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome
                nomeDuplicado = fabricantesMedicamentoRepository.existsByNome(request.getNome().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome
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
        
        // Preserva campos de controle
        java.util.UUID idOriginal = fabricantesMedicamento.getId();
        Boolean activeOriginal = fabricantesMedicamento.getActive();
        java.time.OffsetDateTime createdAtOriginal = fabricantesMedicamento.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(fabricantesMedicamentoAtualizado, fabricantesMedicamento);
        
        // Restaura campos de controle
        fabricantesMedicamento.setId(idOriginal);
        fabricantesMedicamento.setActive(activeOriginal);
        fabricantesMedicamento.setCreatedAt(createdAtOriginal);
    }
}
