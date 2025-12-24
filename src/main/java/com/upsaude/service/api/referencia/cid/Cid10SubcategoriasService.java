package com.upsaude.service.api.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface Cid10SubcategoriasService {

    Cid10Subcategorias criar(Cid10Subcategorias entity);

    Cid10Subcategorias obterPorId(UUID id);

    Page<Cid10Subcategorias> listar(Pageable pageable);

    Cid10Subcategorias atualizar(UUID id, Cid10Subcategorias entity);

    void excluir(UUID id);

    java.util.List<Cid10Subcategorias> listarPorCategoria(String categoriaCat);
}

