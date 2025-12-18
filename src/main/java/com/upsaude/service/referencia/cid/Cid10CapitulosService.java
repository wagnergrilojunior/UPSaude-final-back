package com.upsaude.service.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Capitulos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface Cid10CapitulosService {

    Cid10Capitulos criar(Cid10Capitulos entity);

    Cid10Capitulos obterPorId(UUID id);

    Page<Cid10Capitulos> listar(Pageable pageable);

    Cid10Capitulos atualizar(UUID id, Cid10Capitulos entity);

    void excluir(UUID id);
}

