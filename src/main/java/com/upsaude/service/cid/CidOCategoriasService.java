package com.upsaude.service.cid;

import com.upsaude.entity.cid.CidOCategorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CidOCategoriasService {

    CidOCategorias criar(CidOCategorias entity);

    CidOCategorias obterPorId(UUID id);

    Page<CidOCategorias> listar(Pageable pageable);

    CidOCategorias atualizar(UUID id, CidOCategorias entity);

    void excluir(UUID id);
}

