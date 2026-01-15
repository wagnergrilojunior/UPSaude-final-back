package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CompetenciaFinanceira> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.tenant.id = :tenantId")
    Page<CompetenciaFinanceira> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.codigo = :codigo AND c.tenant.id = :tenantId")
    Optional<CompetenciaFinanceira> findByCodigoAndTenant(@Param("codigo") String codigo, @Param("tenantId") UUID tenantId);

    boolean existsByCodigoAndTenant_Id(String codigo, UUID tenantId);

    boolean existsByCodigoAndTenant_IdAndIdNot(String codigo, UUID tenantId, UUID id);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.tipo = :tipo AND c.tenant.id = :tenantId ORDER BY c.dataInicio DESC")
    List<CompetenciaFinanceira> findByTipoAndTenant(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.dataInicio BETWEEN :inicio AND :fim AND c.tenant.id = :tenantId ORDER BY c.dataInicio DESC")
    List<CompetenciaFinanceira> findByDataInicioBetweenAndTenant(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.dataFim BETWEEN :inicio AND :fim AND c.tenant.id = :tenantId ORDER BY c.dataFim DESC")
    List<CompetenciaFinanceira> findByDataFimBetweenAndTenant(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE :data BETWEEN c.dataInicio AND c.dataFim AND c.tenant.id = :tenantId")
    List<CompetenciaFinanceira> findByDataDentroDoPeriodoAndTenant(@Param("data") LocalDate data, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.status = :status AND c.tenant.id = :tenantId ORDER BY c.dataInicio DESC")
    Page<CompetenciaFinanceira> findByStatusAndTenant(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Deprecated
    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.codigo = :codigo")
    Optional<CompetenciaFinanceira> findByCodigo(@Param("codigo") String codigo);

    @Deprecated
    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.tipo = :tipo ORDER BY c.dataInicio DESC")
    List<CompetenciaFinanceira> findByTipo(@Param("tipo") String tipo);

    @Deprecated
    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.dataInicio BETWEEN :inicio AND :fim ORDER BY c.dataInicio DESC")
    List<CompetenciaFinanceira> findByDataInicioBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    @Deprecated
    @Query("SELECT c FROM CompetenciaFinanceira c WHERE c.dataFim BETWEEN :inicio AND :fim ORDER BY c.dataFim DESC")
    List<CompetenciaFinanceira> findByDataFimBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    @Deprecated
    @Query("SELECT c FROM CompetenciaFinanceira c WHERE :data BETWEEN c.dataInicio AND c.dataFim")
    List<CompetenciaFinanceira> findByDataDentroDoPeriodo(@Param("data") LocalDate data);
}
