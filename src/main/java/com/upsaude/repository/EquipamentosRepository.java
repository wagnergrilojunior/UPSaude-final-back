package com.upsaude.repository;

import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, UUID> {

    Optional<Equipamentos> findByCodigoCnes(String codigoCnes);

    Optional<Equipamentos> findByRegistroAnvisa(String registroAnvisa);

    Page<Equipamentos> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);

    Page<Equipamentos> findByTipoOrderByNomeAsc(TipoEquipamentoEnum tipo, Pageable pageable);

    Page<Equipamentos> findByFabricanteIdOrderByNomeAsc(UUID fabricanteId, Pageable pageable);

    Page<Equipamentos> findByStatusOrderByNomeAsc(StatusAtivoEnum status, Pageable pageable);

    Page<Equipamentos> findByStatusAndActiveTrueOrderByNomeAsc(StatusAtivoEnum status, Pageable pageable);

    Page<Equipamentos> findByTenantOrderByNomeAsc(Tenant tenant, Pageable pageable);
}
