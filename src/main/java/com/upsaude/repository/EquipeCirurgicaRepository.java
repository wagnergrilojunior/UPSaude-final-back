package com.upsaude.repository;

import com.upsaude.entity.EquipeCirurgica;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipeCirurgicaRepository extends JpaRepository<EquipeCirurgica, UUID> {

    List<EquipeCirurgica> findByCirurgiaIdOrderByFuncaoAsc(UUID cirurgiaId);

    Page<EquipeCirurgica> findByCirurgiaIdOrderByFuncaoAsc(UUID cirurgiaId, Pageable pageable);

    Page<EquipeCirurgica> findByProfissionalIdOrderByCreatedAtDesc(UUID profissionalId, Pageable pageable);

    Page<EquipeCirurgica> findByProfissionalIdAndFuncaoOrderByCreatedAtDesc(
            UUID profissionalId, String funcao, Pageable pageable);

    Page<EquipeCirurgica> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}
