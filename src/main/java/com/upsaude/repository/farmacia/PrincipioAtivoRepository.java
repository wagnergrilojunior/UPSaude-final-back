package com.upsaude.repository.farmacia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.farmacia.PrincipioAtivo;

@Repository
public interface PrincipioAtivoRepository extends JpaRepository<PrincipioAtivo, UUID> {

    Optional<PrincipioAtivo> findByCodigoFhir(String codigoFhir);

    Page<PrincipioAtivo> findByAtivoTrue(Pageable pageable);

    @Query("SELECT p FROM PrincipioAtivo p WHERE p.ativo = true AND (" +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<PrincipioAtivo> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigoFhir(String codigoFhir);
}
