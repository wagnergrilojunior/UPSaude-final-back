package com.upsaude.service.impl;

import com.upsaude.entity.cid.CidOGrupos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.cid.CidOGruposRepository;
import com.upsaude.service.cid.CidOGruposService;
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
public class CidOGruposServiceImpl implements CidOGruposService {

    private final CidOGruposRepository repository;

    @Override
    @Transactional
    public CidOGrupos criar(CidOGrupos entity) {
        if (entity == null) {
            throw new BadRequestException("Dados do grupo CID-O s?o obrigat?rios");
        }
        try {
            return repository.save(Objects.requireNonNull(entity));
        } catch (DataAccessException e) {
            log.error("Erro ao persistir grupo CID-O. Entity: {}", entity, e);
            throw new InternalServerErrorException("Erro ao persistir grupo CID-O", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CidOGrupos obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do grupo CID-O ? obrigat?rio");
        }
        try {
            return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo CID-O n?o encontrado com ID: " + id));
        } catch (DataAccessException e) {
            log.error("Erro ao buscar grupo CID-O por ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar grupo CID-O", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidOGrupos> listar(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (DataAccessException e) {
            log.error("Erro ao listar grupos CID-O. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar grupos CID-O", e);
        }
    }

    @Override
    @Transactional
    public CidOGrupos atualizar(UUID id, CidOGrupos entity) {
        if (id == null) {
            throw new BadRequestException("ID do grupo CID-O ? obrigat?rio");
        }
        if (entity == null) {
            throw new BadRequestException("Dados do grupo CID-O s?o obrigat?rios");
        }

        try {
            CidOGrupos atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo CID-O n?o encontrado com ID: " + id));

            atual.setCatinic(entity.getCatinic());
            atual.setCatfim(entity.getCatfim());
            atual.setDescricao(entity.getDescricao());
            atual.setRefer(entity.getRefer());

            return repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao atualizar grupo CID-O. ID: {}, Entity: {}", id, entity, e);
            throw new InternalServerErrorException("Erro ao atualizar grupo CID-O", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do grupo CID-O ? obrigat?rio");
        }
        try {
            CidOGrupos atual = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo CID-O n?o encontrado com ID: " + id));
            atual.setActive(false);
            repository.save(atual);
        } catch (DataAccessException e) {
            log.error("Erro ao excluir (inativar) grupo CID-O. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir grupo CID-O", e);
        }
    }
}

