package com.upsaude.repository.alergia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.alergia.Alergeno;

@Repository
public interface AlergenoRepository extends JpaRepository<Alergeno, UUID> {

    Optional<Alergeno> findByCodigoFhir(String codigoFhir);

    Page<Alergeno> findByAtivoTrue(Pageable pageable);

    Page<Alergeno> findByCategoriaAndAtivoTrue(String categoria, Pageable pageable);

    @Query("SELECT a FROM Alergeno a WHERE a.ativo = true AND (" +
            "LOWER(a.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(a.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<Alergeno> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigoFhir(String codigoFhir);
}
