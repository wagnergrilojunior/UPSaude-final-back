package com.upsaude.service.impl;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MovimentacoesEstoqueMapper;
import com.upsaude.repository.MovimentacoesEstoqueRepository;
import com.upsaude.service.MovimentacoesEstoqueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de MovimentacoesEstoque.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueServiceImpl implements MovimentacoesEstoqueService {

    private final MovimentacoesEstoqueRepository movimentacoesEstoqueRepository;
    private final MovimentacoesEstoqueMapper movimentacoesEstoqueMapper;

    @Override
    @Transactional
    public MovimentacoesEstoqueResponse criar(MovimentacoesEstoqueRequest request) {
        log.debug("Criando novo movimentacoesestoque");

        validarDadosBasicos(request);

        MovimentacoesEstoque movimentacoesEstoque = movimentacoesEstoqueMapper.fromRequest(request);
        movimentacoesEstoque.setActive(true);

        MovimentacoesEstoque movimentacoesEstoqueSalvo = movimentacoesEstoqueRepository.save(movimentacoesEstoque);
        log.info("MovimentacoesEstoque criado com sucesso. ID: {}", movimentacoesEstoqueSalvo.getId());

        return movimentacoesEstoqueMapper.toResponse(movimentacoesEstoqueSalvo);
    }

    @Override
    @Transactional
    public MovimentacoesEstoqueResponse obterPorId(UUID id) {
        log.debug("Buscando movimentacoesestoque por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do movimentacoesestoque é obrigatório");
        }

        MovimentacoesEstoque movimentacoesEstoque = movimentacoesEstoqueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MovimentacoesEstoque não encontrado com ID: " + id));

        return movimentacoesEstoqueMapper.toResponse(movimentacoesEstoque);
    }

    @Override
    public Page<MovimentacoesEstoqueResponse> listar(Pageable pageable) {
        log.debug("Listando MovimentacoesEstoques paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MovimentacoesEstoque> movimentacoesEstoques = movimentacoesEstoqueRepository.findAll(pageable);
        return movimentacoesEstoques.map(movimentacoesEstoqueMapper::toResponse);
    }

    @Override
    @Transactional
    public MovimentacoesEstoqueResponse atualizar(UUID id, MovimentacoesEstoqueRequest request) {
        log.debug("Atualizando movimentacoesestoque. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do movimentacoesestoque é obrigatório");
        }

        validarDadosBasicos(request);

        MovimentacoesEstoque movimentacoesEstoqueExistente = movimentacoesEstoqueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MovimentacoesEstoque não encontrado com ID: " + id));

        atualizarDadosMovimentacoesEstoque(movimentacoesEstoqueExistente, request);

        MovimentacoesEstoque movimentacoesEstoqueAtualizado = movimentacoesEstoqueRepository.save(movimentacoesEstoqueExistente);
        log.info("MovimentacoesEstoque atualizado com sucesso. ID: {}", movimentacoesEstoqueAtualizado.getId());

        return movimentacoesEstoqueMapper.toResponse(movimentacoesEstoqueAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo movimentacoesestoque. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do movimentacoesestoque é obrigatório");
        }

        MovimentacoesEstoque movimentacoesEstoque = movimentacoesEstoqueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MovimentacoesEstoque não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(movimentacoesEstoque.getActive())) {
            throw new BadRequestException("MovimentacoesEstoque já está inativo");
        }

        movimentacoesEstoque.setActive(false);
        movimentacoesEstoqueRepository.save(movimentacoesEstoque);
        log.info("MovimentacoesEstoque excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MovimentacoesEstoqueRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do movimentacoesestoque são obrigatórios");
        }
    }

        private void atualizarDadosMovimentacoesEstoque(MovimentacoesEstoque movimentacoesEstoque, MovimentacoesEstoqueRequest request) {
        MovimentacoesEstoque movimentacoesEstoqueAtualizado = movimentacoesEstoqueMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = movimentacoesEstoque.getId();
        com.upsaude.entity.Tenant tenantOriginal = movimentacoesEstoque.getTenant();
        Boolean activeOriginal = movimentacoesEstoque.getActive();
        java.time.OffsetDateTime createdAtOriginal = movimentacoesEstoque.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(movimentacoesEstoqueAtualizado, movimentacoesEstoque);
        
        // Restaura campos de controle
        movimentacoesEstoque.setId(idOriginal);
        movimentacoesEstoque.setTenant(tenantOriginal);
        movimentacoesEstoque.setActive(activeOriginal);
        movimentacoesEstoque.setCreatedAt(createdAtOriginal);
    }
}
