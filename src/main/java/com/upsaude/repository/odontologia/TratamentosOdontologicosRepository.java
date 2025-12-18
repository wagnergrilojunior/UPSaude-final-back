package com.upsaude.repository.odontologia;

import java.util.UUID;
import java.util.Optional;
import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.entity.TratamentosOdontologicos.StatusTratamento;

public interface TratamentosOdontologicosRepository extends JpaRepository<TratamentosOdontologicos, UUID> {

    Page<TratamentosOdontologicos> findByEstabelecimentoIdOrderByDataInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<TratamentosOdontologicos> findByTenantOrderByDataInicioDesc(Tenant tenant, Pageable pageable);

    Page<TratamentosOdontologicos> findByEstabelecimentoIdAndTenantOrderByDataInicioDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT t FROM TratamentosOdontologicos t WHERE t.id = :id AND t.tenant.id = :tenantId")
    Optional<TratamentosOdontologicos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT t FROM TratamentosOdontologicos t WHERE t.tenant.id = :tenantId ORDER BY t.dataInicio DESC")
    Page<TratamentosOdontologicos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<TratamentosOdontologicos> findByEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<TratamentosOdontologicos> findByPacienteIdAndTenantIdOrderByDataInicioDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<TratamentosOdontologicos> findByProfissionalIdAndTenantIdOrderByDataInicioDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<TratamentosOdontologicos> findByStatusAndTenantIdOrderByDataInicioDesc(StatusTratamento status, UUID tenantId, Pageable pageable);

    Page<TratamentosOdontologicos> findByDataInicioBetweenAndTenantIdOrderByDataInicioDesc(OffsetDateTime inicio, OffsetDateTime fim, UUID tenantId, Pageable pageable);
}
