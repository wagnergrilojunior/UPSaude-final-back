package com.upsaude.repository.vacinacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.ViaAdministracao;

@Repository
public interface ViaAdministracaoRepository extends JpaRepository<ViaAdministracao, UUID> {

    Optional<ViaAdministracao> findByCodigoFhir(String codigoFhir);

    List<ViaAdministracao> findByAtivoTrueOrderByNomeAsc();

    Page<ViaAdministracao> findByAtivoTrue(Pageable pageable);

    @Query("SELECT v FROM ViaAdministracao v WHERE v.ativo = true AND (" +
            "LOWER(v.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(v.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<ViaAdministracao> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigoFhir(String codigoFhir);

    long countByAtivoTrue();
}
