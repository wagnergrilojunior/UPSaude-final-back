package com.upsaude.repository.clinica.medicacao;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.medicacao.ReceitasMedicas;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.StatusReceitaEnum;

public interface ReceitasMedicasRepository extends JpaRepository<ReceitasMedicas, UUID> {

    Page<ReceitasMedicas> findByEstabelecimentoIdOrderByDataPrescricaoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ReceitasMedicas> findByTenantOrderByDataPrescricaoDesc(Tenant tenant, Pageable pageable);

    Page<ReceitasMedicas> findByEstabelecimentoIdAndTenantOrderByDataPrescricaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT r FROM ReceitasMedicas r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<ReceitasMedicas> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM ReceitasMedicas r WHERE r.tenant.id = :tenantId ORDER BY r.dataPrescricao DESC")
    Page<ReceitasMedicas> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByEstabelecimentoIdAndTenantIdOrderByDataPrescricaoDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByPacienteIdAndTenantIdOrderByDataPrescricaoDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByMedicoIdAndTenantIdOrderByDataPrescricaoDesc(UUID medicoId, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByStatusAndTenantIdOrderByDataPrescricaoDesc(StatusReceitaEnum status, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByDataPrescricaoBetweenAndTenantIdOrderByDataPrescricaoDesc(OffsetDateTime inicio, OffsetDateTime fim, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByNumeroReceitaContainingIgnoreCaseAndTenantId(String numeroReceita, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByUsoContinuoAndTenantId(Boolean usoContinuo, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByOrigemReceitaContainingIgnoreCaseAndTenantId(String origemReceita, UUID tenantId, Pageable pageable);

    Page<ReceitasMedicas> findByCidPrincipalIdAndTenantId(UUID cidPrincipalId, UUID tenantId, Pageable pageable);
}
