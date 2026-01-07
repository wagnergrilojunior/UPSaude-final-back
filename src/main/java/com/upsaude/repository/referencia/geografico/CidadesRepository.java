package com.upsaude.repository.referencia.geografico;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.referencia.geografico.Cidades;

public interface CidadesRepository extends JpaRepository<Cidades, UUID> {

    @Query("SELECT c FROM Cidades c WHERE c.estado.id = :estadoId ORDER BY c.nome")
    Page<Cidades> findByEstadoId(@Param("estadoId") UUID estadoId, Pageable pageable);

    Optional<Cidades> findByCodigoIbge(String codigoIbge);

    @Query("SELECT c FROM Cidades c WHERE c.nome = :nome AND c.estado.id = :estadoId")
    Optional<Cidades> findByNomeAndEstadoId(@Param("nome") String nome, @Param("estadoId") UUID estadoId);
}

