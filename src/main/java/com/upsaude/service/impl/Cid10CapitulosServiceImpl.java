package com.upsaude.service.impl;

import com.upsaude.entity.cid.Cid10Capitulos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.cid.Cid10CapitulosRepository;
import com.upsaude.service.cid.Cid10CapitulosService;
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
public class Cid10CapitulosServiceImpl implements Cid10CapitulosService {

    private final Cid10CapitulosRepository repository;

    @Override
    @Transactional
    public Cid10Capitulos criar(Cid10Capitulos entity) {
        if (entity == null) {
            throw new BadRequestException("Dados do cap?tulo CID-10 s?o obrigat?rios");
        }
        try {
            return repository.save(Objects.requireNonNull(entity));
        } catch (DataAccessException e) {
            log.error("Erro ao persistir cap?tulo CID-10. Entity: {}", entity, e);
            throw new InternalServerErrorException("Erro ao persistir cap?tulo CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cid10Capitulos obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do cap?tulo CID-10 ? obrigat?rio");
        }
        try {
            return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cap?tulo CID-10 n?o encontrado com ID: " + id));
        } catch (DataAccessException e) {
            log.error("Erro ao buscar cap?tulo CID-10 por ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar cap?tulo CID-10", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cid10Capitulos> listar(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (DataAccessException e) {
            log.error("Erro ao listar cap?tulos CID-10. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar cap?tulos CID-10", e);
        }
    }

    @Override
    @Transactional
    public Cid10Capitulos atualizar(UUID id, Cid10Capitulos entity) {
        if (id == null) {
            throw new BadRequestException("ID do cap?tulo CID-10 ? obrigat?rio");
        }
        if (entity == null) {
            throw new BadRequestException("Dados do cap?tulo CID-10 s?o obrigat?rios");
        }

        try {
            Cid10Capitulos atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cap?tulo CID-10 n?o encontrado com ID: " + id));

            atual.setNumcap(entity.getNumcap());
            atual.setCatinic(entity.getCatinic());
            atual.setCatfim(entity.getCatfim());
            atual.setDescricao(entity.getDescricao());
            atual.setDescricaoAbreviada(entity.getDescricaoAbreviada());

            return repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao atualizar cap?tulo CID-10. ID: {}, Entity: {}", id, entity, e);
            throw new InternalServerErrorException("Erro ao atualizar cap?tulo CID-10", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do cap?tulo CID-10 ? obrigat?rio");
        }
        try {
            Cid10Capitulos atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cap?tulo CID-10 n?o encontrado com ID: " + id));
            atual.setActive(false);
            repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao excluir (inativar) cap?tulo CID-10. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir cap?tulo CID-10", e);
        }
    }
}

