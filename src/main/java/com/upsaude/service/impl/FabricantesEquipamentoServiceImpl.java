package com.upsaude.service.impl;

import com.upsaude.api.request.FabricantesEquipamentoRequest;
import com.upsaude.api.response.FabricantesEquipamentoResponse;
import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesEquipamentoMapper;
import com.upsaude.repository.FabricantesEquipamentoRepository;
import com.upsaude.service.FabricantesEquipamentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de FabricantesEquipamento.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoServiceImpl implements FabricantesEquipamentoService {

    private final FabricantesEquipamentoRepository fabricantesEquipamentoRepository;
    private final FabricantesEquipamentoMapper fabricantesEquipamentoMapper;

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesequipamento", allEntries = true)
    public FabricantesEquipamentoResponse criar(FabricantesEquipamentoRequest request) {
        log.debug("Criando novo fabricante de equipamento");

        validarDadosBasicos(request);
        validarDuplicidade(null, request);

        FabricantesEquipamento fabricantesEquipamento = fabricantesEquipamentoMapper.fromRequest(request);
        fabricantesEquipamento.setActive(true);

        FabricantesEquipamento fabricantesEquipamentoSalvo = fabricantesEquipamentoRepository.save(fabricantesEquipamento);
        log.info("Fabricante de equipamento criado com sucesso. ID: {}", fabricantesEquipamentoSalvo.getId());

        return fabricantesEquipamentoMapper.toResponse(fabricantesEquipamentoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "fabricantesequipamento", key = "#id")
    public FabricantesEquipamentoResponse obterPorId(UUID id) {
        log.debug("Buscando fabricante de equipamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        FabricantesEquipamento fabricantesEquipamento = fabricantesEquipamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        return fabricantesEquipamentoMapper.toResponse(fabricantesEquipamento);
    }

    @Override
    public Page<FabricantesEquipamentoResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesEquipamento paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesEquipamento> fabricantesEquipamentos = fabricantesEquipamentoRepository.findAll(pageable);
        return fabricantesEquipamentos.map(fabricantesEquipamentoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesequipamento", key = "#id")
    public FabricantesEquipamentoResponse atualizar(UUID id, FabricantesEquipamentoRequest request) {
        log.debug("Atualizando fabricante de equipamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        validarDadosBasicos(request);

        FabricantesEquipamento fabricantesEquipamentoExistente = fabricantesEquipamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        validarDuplicidade(id, request);

        atualizarDadosFabricantesEquipamento(fabricantesEquipamentoExistente, request);

        FabricantesEquipamento fabricantesEquipamentoAtualizado = fabricantesEquipamentoRepository.save(fabricantesEquipamentoExistente);
        log.info("Fabricante de equipamento atualizado com sucesso. ID: {}", fabricantesEquipamentoAtualizado.getId());

        return fabricantesEquipamentoMapper.toResponse(fabricantesEquipamentoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesequipamento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo fabricante de equipamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        FabricantesEquipamento fabricantesEquipamento = fabricantesEquipamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(fabricantesEquipamento.getActive())) {
            throw new BadRequestException("Fabricante de equipamento já está inativo");
        }

        fabricantesEquipamento.setActive(false);
        fabricantesEquipamentoRepository.save(fabricantesEquipamento);
        log.info("Fabricante de equipamento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(FabricantesEquipamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricante de equipamento são obrigatórios");
        }
    }

    /**
     * Valida se já existe um fabricante de equipamento com o mesmo nome ou CNPJ no banco de dados.
     * 
     * @param id ID do fabricante de equipamento sendo atualizado (null para criação)
     * @param request dados do fabricante de equipamento sendo cadastrado/atualizado
     * @throws BadRequestException se já existe um fabricante de equipamento com o mesmo nome ou CNPJ
     */
    private void validarDuplicidade(UUID id, FabricantesEquipamentoRequest request) {
        if (request == null) {
            return;
        }

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome
                nomeDuplicado = fabricantesEquipamentoRepository.existsByNome(request.getNome().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome
                nomeDuplicado = fabricantesEquipamentoRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de equipamento com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe um fabricante de equipamento cadastrado com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        // Valida duplicidade do CNPJ (apenas se fornecido)
        if (request.getCnpj() != null && !request.getCnpj().trim().isEmpty()) {
            boolean cnpjDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este CNPJ
                cnpjDuplicado = fabricantesEquipamentoRepository.existsByCnpj(request.getCnpj().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este CNPJ
                cnpjDuplicado = fabricantesEquipamentoRepository.existsByCnpjAndIdNot(request.getCnpj().trim(), id);
            }

            if (cnpjDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de equipamento com CNPJ duplicado. CNPJ: {}", request.getCnpj());
                throw new BadRequestException(
                    String.format("Já existe um fabricante de equipamento cadastrado com o CNPJ '%s' no banco de dados", request.getCnpj())
                );
            }
        }
    }

    private void atualizarDadosFabricantesEquipamento(FabricantesEquipamento fabricantesEquipamento, FabricantesEquipamentoRequest request) {
        FabricantesEquipamento fabricantesEquipamentoAtualizado = fabricantesEquipamentoMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = fabricantesEquipamento.getId();
        Boolean activeOriginal = fabricantesEquipamento.getActive();
        java.time.OffsetDateTime createdAtOriginal = fabricantesEquipamento.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(fabricantesEquipamentoAtualizado, fabricantesEquipamento);
        
        // Restaura campos de controle
        fabricantesEquipamento.setId(idOriginal);
        fabricantesEquipamento.setActive(activeOriginal);
        fabricantesEquipamento.setCreatedAt(createdAtOriginal);
    }
}
