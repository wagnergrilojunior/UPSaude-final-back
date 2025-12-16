package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CidDoencas;

public interface CidDoencasRepository extends JpaRepository<CidDoencas, UUID> {

    Optional<CidDoencas> findByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, UUID id);
}
