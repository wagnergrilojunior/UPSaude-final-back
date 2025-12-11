package com.upsaude.repository;

import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
