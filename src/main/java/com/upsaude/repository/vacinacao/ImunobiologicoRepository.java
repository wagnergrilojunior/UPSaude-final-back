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

import com.upsaude.entity.vacinacao.Imunobiologico;

@Repository
public interface ImunobiologicoRepository extends JpaRepository<Imunobiologico, UUID> {

    Optional<Imunobiologico> findByCodigoFhir(String codigoFhir);

    List<Imunobiologico> findByAtivoTrueOrderByNomeAsc();

    List<Imunobiologico> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    @Query("SELECT i FROM Imunobiologico i WHERE " +
            "LOWER(i.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(i.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Imunobiologico> buscarPorTermo(@Param("termo") String termo);

    boolean existsByCodigoFhir(String codigoFhir);

    long countByAtivoTrue();

    Page<Imunobiologico> findByAtivoTrue(Pageable pageable);

    @Query("SELECT i FROM Imunobiologico i WHERE " +
            "LOWER(i.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(i.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Imunobiologico> buscarPorTermoPaginado(@Param("termo") String termo, Pageable pageable);
}
