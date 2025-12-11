package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Deficiencias;

public interface DeficienciasRepository extends JpaRepository<Deficiencias, UUID> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);
}
