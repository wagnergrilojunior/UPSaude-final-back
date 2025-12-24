package com.upsaude.service.impl.api.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Categorias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.cid.Cid10CategoriasRepository;
import com.upsaude.service.api.referencia.cid.Cid10CategoriasService;
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
public class Cid10CategoriasServiceImpl implements Cid10CategoriasService {

    private final Cid10CategoriasRepository repository;

    @Override
    @Transactional
    public Cid10Categorias criar(Cid10Categorias entity) {
        if (entity == null) {
            throw new BadRequestException("Dados da categoria CID-10 s?o obrigat?rios");
        }
        try {
            return repository.save(Objects.requireNonNull(entity));
        } catch (DataAccessException e) {
            log.error("Erro ao persistir categoria CID-10. Entity: {}", entity, e);
            throw new InternalServerErrorException("Erro ao persistir categoria CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cid10Categorias obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da categoria CID-10 ? obrigat?rio");
        }
        try {
            return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria CID-10 n?o encontrada com ID: " + id));
        } catch (DataAccessException e) {
            log.error("Erro ao buscar categoria CID-10 por ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar categoria CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cid10Categorias> listar(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (DataAccessException e) {
            log.error("Erro ao listar categorias CID-10. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar categorias CID-10", e);
        }
    }

    @Override
    @Transactional
    public Cid10Categorias atualizar(UUID id, Cid10Categorias entity) {
        if (id == null) {
            throw new BadRequestException("ID da categoria CID-10 ? obrigat?rio");
        }
        if (entity == null) {
            throw new BadRequestException("Dados da categoria CID-10 s?o obrigat?rios");
        }

        try {
            Cid10Categorias atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria CID-10 n?o encontrada com ID: " + id));

            atual.setCat(entity.getCat());
            atual.setClassif(entity.getClassif());
            atual.setDescricao(entity.getDescricao());
            atual.setDescrAbrev(entity.getDescrAbrev());
            atual.setRefer(entity.getRefer());
            atual.setExcluidos(entity.getExcluidos());

            return repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao atualizar categoria CID-10. ID: {}, Entity: {}", id, entity, e);
            throw new InternalServerErrorException("Erro ao atualizar categoria CID-10", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da categoria CID-10 ? obrigat?rio");
        }
        try {
            Cid10Categorias atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria CID-10 n?o encontrada com ID: " + id));
            atual.setActive(false);
            repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao excluir (inativar) categoria CID-10. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir categoria CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Cid10Categorias> listarPorIntervalo(String catinic, String catfim) {
        if (catinic == null || catfim == null) {
            throw new BadRequestException("Intervalo (catinic e catfim) são obrigatórios");
        }
        try {
            return repository.findByIntervalo(catinic, catfim);
        } catch (DataAccessException e) {
            log.error("Erro ao listar categorias CID-10 por intervalo. catinic: {}, catfim: {}", catinic, catfim, e);
            throw new InternalServerErrorException("Erro ao listar categorias CID-10 por intervalo", e);
        }
    }
}

