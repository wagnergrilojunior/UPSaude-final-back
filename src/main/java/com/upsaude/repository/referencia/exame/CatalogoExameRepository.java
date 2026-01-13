package com.upsaude.repository.referencia.exame;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.referencia.exame.CatalogoExame;

public interface CatalogoExameRepository extends JpaRepository<CatalogoExame, UUID> {

    @Query("SELECT e FROM CatalogoExame e WHERE e.sourceSystem = :source AND e.externalCode = :code")
    Optional<CatalogoExame> findBySourceAndCode(@Param("source") String source, @Param("code") String code);

    @Query("SELECT e FROM CatalogoExame e WHERE e.codigoLoinc = :codigo")
    Optional<CatalogoExame> findByCodigoLoinc(@Param("codigo") String codigo);

    @Query("SELECT e FROM CatalogoExame e WHERE e.codigoGal = :codigo")
    Optional<CatalogoExame> findByCodigoGal(@Param("codigo") String codigo);

    @Query("SELECT e FROM CatalogoExame e WHERE e.codigoSigtap = :codigo")
    Optional<CatalogoExame> findByCodigoSigtap(@Param("codigo") String codigo);

    @Query("SELECT e FROM CatalogoExame e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<CatalogoExame> searchByNome(@Param("termo") String termo);

    @Query("SELECT e FROM CatalogoExame e WHERE e.sourceSystem = :source")
    List<CatalogoExame> findBySource(@Param("source") String source);
}
