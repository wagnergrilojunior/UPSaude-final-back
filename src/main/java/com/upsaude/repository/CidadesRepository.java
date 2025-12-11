package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Cidades;

public interface CidadesRepository extends JpaRepository<Cidades, UUID> {

    @Query("SELECT c FROM Cidades c WHERE c.estado.id = :estadoId ORDER BY c.nome")
    Page<Cidades> findByEstadoId(@Param("estadoId") UUID estadoId, Pageable pageable);
}
