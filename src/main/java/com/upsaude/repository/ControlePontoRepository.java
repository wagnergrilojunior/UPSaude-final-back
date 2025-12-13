package com.upsaude.repository;

import com.upsaude.entity.ControlePonto;
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
public interface ControlePontoRepository extends JpaRepository<ControlePonto, UUID> {

    @Query("SELECT c FROM ControlePonto c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ControlePonto> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ControlePonto c WHERE c.tenant.id = :tenantId")
    Page<ControlePonto> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ControlePonto> findByProfissionalIdAndTenantIdOrderByDataHoraDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<ControlePonto> findByMedicoIdAndTenantIdOrderByDataHoraDesc(UUID medicoId, UUID tenantId, Pageable pageable);

    Page<ControlePonto> findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<ControlePonto> findByProfissionalIdAndTenantIdAndDataPontoOrderByDataHoraAsc(UUID profissionalId, UUID tenantId, LocalDate dataPonto, Pageable pageable);

    Page<ControlePonto> findByProfissionalIdAndTenantIdAndDataPontoBetweenOrderByDataHoraAsc(UUID profissionalId, UUID tenantId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<ControlePonto> findByEstabelecimentoIdAndTenantIdAndDataPontoBetweenOrderByDataHoraAsc(UUID estabelecimentoId, UUID tenantId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
}
