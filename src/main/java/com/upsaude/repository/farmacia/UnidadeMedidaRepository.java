package com.upsaude.repository.farmacia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.farmacia.UnidadeMedida;

@Repository
public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, UUID> {

    Optional<UnidadeMedida> findByCodigoFhir(String codigoFhir);

    Page<UnidadeMedida> findByAtivoTrue(Pageable pageable);

    @Query("SELECT u FROM UnidadeMedida u WHERE u.ativo = true AND (" +
            "LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(u.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(u.sigla) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<UnidadeMedida> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigoFhir(String codigoFhir);
}
