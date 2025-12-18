package com.upsaude.repository.profissional.equipe;

import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipeSaudeRepository extends JpaRepository<EquipeSaude, UUID> {

    Optional<EquipeSaude> findByIneAndEstabelecimentoId(String ine, UUID estabelecimentoId);

    Page<EquipeSaude> findByEstabelecimentoIdOrderByNomeReferenciaAsc(UUID estabelecimentoId, Pageable pageable);

    Page<EquipeSaude> findByTenantOrderByNomeReferenciaAsc(Tenant tenant, Pageable pageable);

    Page<EquipeSaude> findByEstabelecimentoIdAndTenantOrderByNomeReferenciaAsc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    Page<EquipeSaude> findByStatusAndEstabelecimentoId(StatusAtivoEnum status, UUID estabelecimentoId, Pageable pageable);

    @Query("SELECT e FROM EquipeSaude e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EquipeSaude> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @EntityGraph(attributePaths = {
        "estabelecimento",
        "vinculosProfissionais",
        "vinculosProfissionais.profissional"
    })
    @Query("SELECT e FROM EquipeSaude e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EquipeSaude> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EquipeSaude e WHERE e.tenant.id = :tenantId")
    Page<EquipeSaude> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EquipeSaude e WHERE e.estabelecimento.id = :estabelecimentoId AND e.tenant.id = :tenantId ORDER BY e.nomeReferencia ASC")
    Page<EquipeSaude> findByEstabelecimentoIdAndTenantIdOrderByNomeReferenciaAsc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EquipeSaude e WHERE e.status = :status AND e.estabelecimento.id = :estabelecimentoId AND e.tenant.id = :tenantId")
    Page<EquipeSaude> findByStatusAndEstabelecimentoIdAndTenantId(@Param("status") StatusAtivoEnum status, @Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EquipeSaude e WHERE e.ine = :ine AND e.estabelecimento.id = :estabelecimentoId AND e.tenant.id = :tenantId")
    Optional<EquipeSaude> findByIneAndEstabelecimentoIdAndTenantId(@Param("ine") String ine, @Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId);
}

