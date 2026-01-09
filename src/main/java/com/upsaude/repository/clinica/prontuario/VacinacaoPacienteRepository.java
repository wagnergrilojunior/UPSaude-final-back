package com.upsaude.repository.clinica.prontuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.prontuario.VacinacaoPaciente;

public interface VacinacaoPacienteRepository extends JpaRepository<VacinacaoPaciente, UUID> {

    @Query("SELECT v FROM VacinacaoPaciente v WHERE v.id = :id AND v.tenant.id = :tenantId")
    Optional<VacinacaoPaciente> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT v FROM VacinacaoPaciente v WHERE v.tenant.id = :tenantId")
    Page<VacinacaoPaciente> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT v FROM VacinacaoPaciente v WHERE v.prontuario.id = :prontuarioId AND v.tenant.id = :tenantId ORDER BY v.dataAplicacao DESC")
    Page<VacinacaoPaciente> findByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT v FROM VacinacaoPaciente v WHERE v.prontuario.id = :prontuarioId AND v.tenant.id = :tenantId")
    List<VacinacaoPaciente> findAllByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId);
}

