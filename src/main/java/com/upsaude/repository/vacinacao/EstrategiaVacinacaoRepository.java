package com.upsaude.repository.vacinacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.EstrategiaVacinacao;

@Repository
public interface EstrategiaVacinacaoRepository extends JpaRepository<EstrategiaVacinacao, UUID> {

    Optional<EstrategiaVacinacao> findByCodigoFhir(String codigoFhir);

    List<EstrategiaVacinacao> findByAtivoTrueOrderByNomeAsc();

    boolean existsByCodigoFhir(String codigoFhir);

    long countByAtivoTrue();
}
