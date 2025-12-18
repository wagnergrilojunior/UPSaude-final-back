package com.upsaude.service.impl;

import com.upsaude.entity.referencia.cid.CidOCategorias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.cid.CidOCategoriasRepository;
import com.upsaude.service.referencia.cid.CidOCategoriasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CidOCategoriasServiceImpl implements CidOCategoriasService {

    private final CidOCategoriasRepository repository;

    @Override
    @Transactional
    public CidOCategorias criar(CidOCategorias entity) {
        if (entity == null) {
            throw new BadRequestException("Dados da categoria CID-O s?o obrigat?rios");
        }
        try {
            return repository.save(Objects.requireNonNull(entity));
        } catch (DataAccessException e) {
            log.error("Erro ao persistir categoria CID-O. Entity: {}", entity, e);
            throw new InternalServerErrorException("Erro ao persistir categoria CID-O", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CidOCategorias obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da categoria CID-O ? obrigat?rio");
        }
        try {
            return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria CID-O n?o encontrada com ID: " + id));
        } catch (DataAccessException e) {
            log.error("Erro ao buscar categoria CID-O por ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar categoria CID-O", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidOCategorias> listar(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (DataAccessException e) {
            log.error("Erro ao listar categorias CID-O. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar categorias CID-O", e);
        }
    }

    @Override
    @Transactional
    public CidOCategorias atualizar(UUID id, CidOCategorias entity) {
        if (id == null) {
            throw new BadRequestException("ID da categoria CID-O ? obrigat?rio");
        }
        if (entity == null) {
            throw new BadRequestException("Dados da categoria CID-O s?o obrigat?rios");
        }

        try {
            CidOCategorias atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria CID-O n?o encontrada com ID: " + id));

            atual.setCat(entity.getCat());
            atual.setDescricao(entity.getDescricao());
            atual.setRefer(entity.getRefer());

            return repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao atualizar categoria CID-O. ID: {}, Entity: {}", id, entity, e);
            throw new InternalServerErrorException("Erro ao atualizar categoria CID-O", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da categoria CID-O ? obrigat?rio");
        }
        try {
            CidOCategorias atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria CID-O n?o encontrada com ID: " + id));
            atual.setActive(false);
            repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao excluir (inativar) categoria CID-O. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir categoria CID-O", e);
        }
    }
}

