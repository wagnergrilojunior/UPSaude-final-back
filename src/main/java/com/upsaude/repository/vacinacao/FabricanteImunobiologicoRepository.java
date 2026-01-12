package com.upsaude.repository.vacinacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.FabricanteImunobiologico;

@Repository
public interface FabricanteImunobiologicoRepository extends JpaRepository<FabricanteImunobiologico, UUID> {

    Optional<FabricanteImunobiologico> findByCodigoFhir(String codigoFhir);

    List<FabricanteImunobiologico> findByAtivoTrueOrderByNomeAsc();

    List<FabricanteImunobiologico> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    boolean existsByCodigoFhir(String codigoFhir);

    long countByAtivoTrue();
}
