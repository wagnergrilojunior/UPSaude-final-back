package com.upsaude.service.api.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Categorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface Cid10CategoriasService {

    Cid10Categorias criar(Cid10Categorias entity);

    Cid10Categorias obterPorId(UUID id);

    Page<Cid10Categorias> listar(Pageable pageable);

    Cid10Categorias atualizar(UUID id, Cid10Categorias entity);

    void excluir(UUID id);

    java.util.List<Cid10Categorias> listarPorIntervalo(String catinic, String catfim);
}

