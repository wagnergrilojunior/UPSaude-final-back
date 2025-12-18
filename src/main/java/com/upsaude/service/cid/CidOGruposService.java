package com.upsaude.service.cid;

import com.upsaude.entity.cid.CidOGrupos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CidOGruposService {

    CidOGrupos criar(CidOGrupos entity);

    CidOGrupos obterPorId(UUID id);

    Page<CidOGrupos> listar(Pageable pageable);

    CidOGrupos atualizar(UUID id, CidOGrupos entity);

    void excluir(UUID id);
}

