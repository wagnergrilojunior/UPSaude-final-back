package com.upsaude.repository.estabelecimento.equipamento;

import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, UUID> {

    @Query("SELECT e FROM Equipamentos e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<Equipamentos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Equipamentos e WHERE e.tenant.id = :tenantId")
    Page<Equipamentos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Optional<Equipamentos> findByCodigoCnesAndTenantId(String codigoCnes, UUID tenantId);

    Optional<Equipamentos> findByRegistroAnvisaAndTenantId(String registroAnvisa, UUID tenantId);

    Page<Equipamentos> findByNomeContainingIgnoreCaseAndTenantIdOrderByNomeAsc(String nome, UUID tenantId, Pageable pageable);

    Page<Equipamentos> findByTipoAndTenantIdOrderByNomeAsc(TipoEquipamentoEnum tipo, UUID tenantId, Pageable pageable);

    Page<Equipamentos> findByFabricanteIdAndTenantIdOrderByNomeAsc(UUID fabricanteId, UUID tenantId, Pageable pageable);

    Page<Equipamentos> findByStatusAndTenantIdOrderByNomeAsc(StatusAtivoEnum status, UUID tenantId, Pageable pageable);

    Page<Equipamentos> findByStatusAndActiveTrueAndTenantIdOrderByNomeAsc(StatusAtivoEnum status, UUID tenantId, Pageable pageable);
}
