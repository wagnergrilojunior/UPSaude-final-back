package com.upsaude.repository.vacinacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.LocalAplicacao;

@Repository
public interface LocalAplicacaoRepository extends JpaRepository<LocalAplicacao, UUID> {

    Optional<LocalAplicacao> findByCodigoFhir(String codigoFhir);

    List<LocalAplicacao> findByAtivoTrueOrderByNomeAsc();

    boolean existsByCodigoFhir(String codigoFhir);

    long countByAtivoTrue();
}
