package com.upsaude.repository.alergia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.alergia.ReacaoAdversaCatalogo;

@Repository
public interface ReacaoAdversaCatalogoRepository extends JpaRepository<ReacaoAdversaCatalogo, UUID> {

    Optional<ReacaoAdversaCatalogo> findByCodigoFhir(String codigoFhir);

    Page<ReacaoAdversaCatalogo> findByAtivoTrue(Pageable pageable);

    @Query("SELECT r FROM ReacaoAdversaCatalogo r WHERE r.ativo = true AND (" +
            "LOWER(r.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(r.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<ReacaoAdversaCatalogo> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigoFhir(String codigoFhir);
}
