package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.RenegociacaoReceber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RenegociacaoReceberRepository extends JpaRepository<RenegociacaoReceber, UUID> {

    @Query("SELECT r FROM RenegociacaoReceber r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<RenegociacaoReceber> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM RenegociacaoReceber r WHERE r.tenant.id = :tenantId")
    Page<RenegociacaoReceber> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RenegociacaoReceber r WHERE r.data BETWEEN :inicio AND :fim AND r.tenant.id = :tenantId ORDER BY r.data DESC")
    Page<RenegociacaoReceber> findByDataBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
