package com.upsaude.repository.referencia.sigtap;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;

public interface SigtapOcupacaoRepository extends JpaRepository<SigtapOcupacao, UUID> {

    @Query("SELECT s FROM SigtapOcupacao s WHERE s.codigoOficial = :codigo")
    Optional<SigtapOcupacao> findByCodigo(@Param("codigo") String codigo);

    @Query("SELECT s FROM SigtapOcupacao s WHERE s.codigoCboCompleto = :codigoCbo")
    Optional<SigtapOcupacao> findByCodigoCbo(@Param("codigoCbo") String codigoCbo);

    @Query("SELECT s FROM SigtapOcupacao s WHERE LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    java.util.List<SigtapOcupacao> searchByNome(@Param("termo") String termo);
}
