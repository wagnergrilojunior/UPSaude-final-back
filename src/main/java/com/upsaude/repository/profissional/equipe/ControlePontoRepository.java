package com.upsaude.repository.profissional.equipe;

import com.upsaude.entity.profissional.equipe.ControlePonto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ControlePontoRepository extends JpaRepository<ControlePonto, UUID> {

    @Query("SELECT c FROM ControlePonto c WHERE c.id = :id AND c.tenant.id = :tenantId")
    java.util.Optional<ControlePonto> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ControlePonto c WHERE c.tenant.id = :tenantId")
    Page<ControlePonto> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ControlePonto c WHERE c.profissional.id = :profissionalId AND c.tenant.id = :tenantId ORDER BY c.dataHora DESC")
    Page<ControlePonto> findByProfissionalIdAndTenantIdOrderByDataHoraDesc(@Param("profissionalId") UUID profissionalId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ControlePonto c WHERE c.medico.id = :medicoId AND c.tenant.id = :tenantId ORDER BY c.dataHora DESC")
    Page<ControlePonto> findByMedicoIdAndTenantIdOrderByDataHoraDesc(@Param("medicoId") UUID medicoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ControlePonto c WHERE c.estabelecimento.id = :estabelecimentoId AND c.tenant.id = :tenantId ORDER BY c.dataHora DESC")
    Page<ControlePonto> findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ControlePonto c WHERE c.profissional.id = :profissionalId AND c.dataPonto = :data AND c.tenant.id = :tenantId ORDER BY c.dataHora DESC")
    Page<ControlePonto> findByProfissionalIdAndDataPontoAndTenantIdOrderByDataHoraDesc(@Param("profissionalId") UUID profissionalId, @Param("data") LocalDate data, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ControlePonto c WHERE c.profissional.id = :profissionalId AND c.dataPonto BETWEEN :dataInicio AND :dataFim AND c.tenant.id = :tenantId ORDER BY c.dataHora DESC")
    Page<ControlePonto> findByProfissionalIdAndDataPontoBetweenAndTenantIdOrderByDataHoraDesc(@Param("profissionalId") UUID profissionalId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, @Param("tenantId") UUID tenantId, Pageable pageable);
}
