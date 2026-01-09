package com.upsaude.service.api.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Grupos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface Cid10GruposService {

    Cid10Grupos criar(Cid10Grupos entity);

    Cid10Grupos obterPorId(UUID id);

    Page<Cid10Grupos> listar(Pageable pageable);

    Cid10Grupos atualizar(UUID id, Cid10Grupos entity);

    void excluir(UUID id);

    java.util.List<Cid10Grupos> listarPorIntervalo(String catinic, String catfim);
}
