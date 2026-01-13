package com.upsaude.repository.vacinacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.TipoDose;

@Repository
public interface TipoDoseRepository extends JpaRepository<TipoDose, UUID> {

    Optional<TipoDose> findByCodigoFhir(String codigoFhir);

    List<TipoDose> findByAtivoTrueOrderByOrdemSequenciaAsc();

    boolean existsByCodigoFhir(String codigoFhir);

    long countByAtivoTrue();
}
