package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.RecorrenciaFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecorrenciaFinanceiraRepository extends JpaRepository<RecorrenciaFinanceira, UUID> {

    @Query("SELECT r FROM RecorrenciaFinanceira r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<RecorrenciaFinanceira> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM RecorrenciaFinanceira r WHERE r.tenant.id = :tenantId")
    Page<RecorrenciaFinanceira> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RecorrenciaFinanceira r WHERE r.tipo = :tipo AND r.tenant.id = :tenantId ORDER BY r.proximaGeracaoEm ASC")
    Page<RecorrenciaFinanceira> findByTipo(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RecorrenciaFinanceira r WHERE r.periodicidade = :periodicidade AND r.tenant.id = :tenantId ORDER BY r.proximaGeracaoEm ASC")
    Page<RecorrenciaFinanceira> findByPeriodicidade(@Param("periodicidade") String periodicidade, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RecorrenciaFinanceira r WHERE r.active = true AND r.tenant.id = :tenantId ORDER BY r.proximaGeracaoEm ASC")
    Page<RecorrenciaFinanceira> findByAtivo(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RecorrenciaFinanceira r WHERE r.proximaGeracaoEm <= :dataLimite AND r.active = true AND r.tenant.id = :tenantId ORDER BY r.proximaGeracaoEm ASC")
    List<RecorrenciaFinanceira> findByProximaGeracaoEmLessThanEqual(@Param("dataLimite") OffsetDateTime dataLimite, @Param("tenantId") UUID tenantId);
}
