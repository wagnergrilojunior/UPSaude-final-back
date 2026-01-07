package com.upsaude.repository.clinica.cirurgia;

import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipeCirurgicaRepository extends JpaRepository<EquipeCirurgica, UUID> {

    List<EquipeCirurgica> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId);

    Page<EquipeCirurgica> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId, Pageable pageable);

    @Query("SELECT DISTINCT e FROM EquipeCirurgica e " +
           "JOIN e.profissionais p " +
           "WHERE p.profissional.id = :profissionalId AND e.tenant.id = :tenantId " +
           "ORDER BY e.createdAt DESC")
    Page<EquipeCirurgica> findByProfissionalIdOrderByCreatedAtDesc(
            @Param("profissionalId") UUID profissionalId, 
            @Param("tenantId") UUID tenantId, 
            Pageable pageable);

    @Query("SELECT DISTINCT e FROM EquipeCirurgica e " +
           "JOIN e.profissionais p " +
           "WHERE p.profissional.id = :profissionalId AND p.funcao = :funcao AND e.tenant.id = :tenantId " +
           "ORDER BY e.createdAt DESC")
    Page<EquipeCirurgica> findByProfissionalIdAndFuncaoOrderByCreatedAtDesc(
            @Param("profissionalId") UUID profissionalId, 
            @Param("funcao") String funcao,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT DISTINCT e FROM EquipeCirurgica e " +
           "JOIN e.medicos m " +
           "WHERE m.medico.id = :medicoId AND e.tenant.id = :tenantId " +
           "ORDER BY e.createdAt DESC")
    Page<EquipeCirurgica> findByMedicoIdOrderByCreatedAtDesc(
            @Param("medicoId") UUID medicoId, 
            @Param("tenantId") UUID tenantId, 
            Pageable pageable);

    @Query("SELECT DISTINCT e FROM EquipeCirurgica e " +
           "JOIN e.medicos m " +
           "WHERE m.medico.id = :medicoId AND m.funcao = :funcao AND e.tenant.id = :tenantId " +
           "ORDER BY e.createdAt DESC")
    Page<EquipeCirurgica> findByMedicoIdAndFuncaoOrderByCreatedAtDesc(
            @Param("medicoId") UUID medicoId, 
            @Param("funcao") String funcao,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    Page<EquipeCirurgica> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT e FROM EquipeCirurgica e WHERE e.id = :id AND e.tenant.id = :tenantId")
    java.util.Optional<EquipeCirurgica> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);
}
