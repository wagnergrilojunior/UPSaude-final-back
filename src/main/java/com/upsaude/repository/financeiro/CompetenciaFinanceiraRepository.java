package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetenciaFinanceiraRepository extends JpaRepository<CompetenciaFinanceira, UUID> {

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.codigo = :codigo")
    Optional<CompetenciaFinanceira> findByCodigo(@Param("codigo") String codigo);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.tipo = :tipo ORDER BY c.dataInicio DESC")
    List<CompetenciaFinanceira> findByTipo(@Param("tipo") String tipo);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.dataInicio BETWEEN :inicio AND :fim ORDER BY c.dataInicio DESC")
    List<CompetenciaFinanceira> findByDataInicioBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.dataFim BETWEEN :inicio AND :fim ORDER BY c.dataFim DESC")
    List<CompetenciaFinanceira> findByDataFimBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE :data BETWEEN c.dataInicio AND c.dataFim")
    List<CompetenciaFinanceira> findByDataDentroDoPeriodo(@Param("data") LocalDate data);
}
