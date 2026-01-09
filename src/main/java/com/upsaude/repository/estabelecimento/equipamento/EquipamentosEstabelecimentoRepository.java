package com.upsaude.repository.estabelecimento.equipamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.enums.StatusManutencaoEnum;

@Repository
public interface EquipamentosEstabelecimentoRepository extends JpaRepository<EquipamentosEstabelecimento, UUID> {

    @Query("SELECT e FROM EquipamentosEstabelecimento e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EquipamentosEstabelecimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EquipamentosEstabelecimento e WHERE e.tenant.id = :tenantId")
    Page<EquipamentosEstabelecimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<EquipamentosEstabelecimento> findByEstabelecimentoIdAndTenantIdOrderByEquipamentoIdAsc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Optional<EquipamentosEstabelecimento> findByEstabelecimentoIdAndTenantIdAndNumeroSerie(UUID estabelecimentoId, UUID tenantId, String numeroSerie);

    Page<EquipamentosEstabelecimento> findByEstabelecimentoIdAndStatusManutencaoAndTenantIdOrderByEquipamentoIdAsc(UUID estabelecimentoId, StatusManutencaoEnum statusManutencao, UUID tenantId, Pageable pageable);

    List<EquipamentosEstabelecimento> findByEstabelecimentoIdAndActiveTrueAndTenantIdOrderByEquipamentoIdAsc(UUID estabelecimentoId, UUID tenantId);
}

