package com.upsaude.repository;

import com.upsaude.entity.Doencas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para a entidade Doencas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
@Repository
public interface DoencasRepository extends JpaRepository<Doencas, UUID> {

    Optional<Doencas> findByNome(String nome);

    Page<Doencas> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Doencas> findByCronica(Boolean cronica, Pageable pageable);

    @Query("SELECT d FROM Doencas d WHERE d.cidPrincipal.codigo = :codigoCid")
    Page<Doencas> findByCodigoCid(@Param("codigoCid") String codigoCid, Pageable pageable);
}

