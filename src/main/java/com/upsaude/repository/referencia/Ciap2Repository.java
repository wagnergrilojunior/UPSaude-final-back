package com.upsaude.repository.referencia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.referencia.Ciap2;

@Repository
public interface Ciap2Repository extends JpaRepository<Ciap2, UUID> {

    Optional<Ciap2> findByCodigo(String codigo);

    Page<Ciap2> findByAtivoTrue(Pageable pageable);

    @Query("SELECT c FROM Ciap2 c WHERE c.ativo = true AND (" +
            "LOWER(c.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(c.codigo) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<Ciap2> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    boolean existsByCodigo(String codigo);
}
