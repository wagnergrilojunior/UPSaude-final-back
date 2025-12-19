package com.upsaude.service.impl;

import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.service.referencia.cid.Cid10SubcategoriasService;
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
public class Cid10SubcategoriasServiceImpl implements Cid10SubcategoriasService {

    private final Cid10SubcategoriasRepository repository;

    @Override
    @Transactional
    public Cid10Subcategorias criar(Cid10Subcategorias entity) {
        if (entity == null) {
            throw new BadRequestException("Dados da subcategoria CID-10 s?o obrigat?rios");
        }
        try {
            return repository.save(Objects.requireNonNull(entity));
        } catch (DataAccessException e) {
            log.error("Erro ao persistir subcategoria CID-10. Entity: {}", entity, e);
            throw new InternalServerErrorException("Erro ao persistir subcategoria CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cid10Subcategorias obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da subcategoria CID-10 ? obrigat?rio");
        }
        try {
            return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subcategoria CID-10 n?o encontrada com ID: " + id));
        } catch (DataAccessException e) {
            log.error("Erro ao buscar subcategoria CID-10 por ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar subcategoria CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cid10Subcategorias> listar(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (DataAccessException e) {
            log.error("Erro ao listar subcategorias CID-10. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar subcategorias CID-10", e);
        }
    }

    @Override
    @Transactional
    public Cid10Subcategorias atualizar(UUID id, Cid10Subcategorias entity) {
        if (id == null) {
            throw new BadRequestException("ID da subcategoria CID-10 ? obrigat?rio");
        }
        if (entity == null) {
            throw new BadRequestException("Dados da subcategoria CID-10 s?o obrigat?rios");
        }

        try {
            Cid10Subcategorias atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subcategoria CID-10 n?o encontrada com ID: " + id));

            atual.setSubcat(entity.getSubcat());
            atual.setCategoriaCat(entity.getCategoriaCat());
            atual.setClassif(entity.getClassif());
            atual.setRestrSexo(entity.getRestrSexo());
            atual.setCausaObito(entity.getCausaObito());
            atual.setDescricao(entity.getDescricao());
            atual.setDescrAbrev(entity.getDescrAbrev());
            atual.setRefer(entity.getRefer());
            atual.setExcluidos(entity.getExcluidos());

            return repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao atualizar subcategoria CID-10. ID: {}, Entity: {}", id, entity, e);
            throw new InternalServerErrorException("Erro ao atualizar subcategoria CID-10", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da subcategoria CID-10 ? obrigat?rio");
        }
        try {
            Cid10Subcategorias atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subcategoria CID-10 n?o encontrada com ID: " + id));
            atual.setActive(false);
            repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao excluir (inativar) subcategoria CID-10. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir subcategoria CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Cid10Subcategorias> listarPorCategoria(String categoriaCat) {
        if (categoriaCat == null || categoriaCat.trim().isEmpty()) {
            throw new BadRequestException("Código da categoria é obrigatório");
        }
        try {
            return repository.findByCategoriaCatAndActiveTrue(categoriaCat);
        } catch (DataAccessException e) {
            log.error("Erro ao listar subcategorias CID-10 por categoria. categoriaCat: {}", categoriaCat, e);
            throw new InternalServerErrorException("Erro ao listar subcategorias CID-10 por categoria", e);
        }
    }
}

