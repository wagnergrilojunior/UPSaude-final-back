package com.upsaude.repository.farmacia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.farmacia.Medicamento;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, UUID> {

    Optional<Medicamento> findByCodigoFhir(String codigoFhir);

    Page<Medicamento> findByAtivoTrue(Pageable pageable);

    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND (" +
            "LOWER(m.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(m.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(m.codigoEan) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(m.registroAnvisa) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<Medicamento> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigoFhir(String codigoFhir);
}
