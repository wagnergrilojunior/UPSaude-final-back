package com.upsaude.repository;

import com.upsaude.entity.EquipeCirurgica;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a EquipeCirurgica.
 *
 * @author UPSaúde
 */
@Repository
public interface EquipeCirurgicaRepository extends JpaRepository<EquipeCirurgica, UUID> {

    /**
     * Busca todos os membros da equipe de uma cirurgia, ordenados por função.
     */
    List<EquipeCirurgica> findByCirurgiaIdOrderByFuncaoAsc(UUID cirurgiaId);

    /**
     * Busca todos os membros da equipe de uma cirurgia com paginação.
     */
    Page<EquipeCirurgica> findByCirurgiaIdOrderByFuncaoAsc(UUID cirurgiaId, Pageable pageable);

    /**
     * Busca todas as participações de um profissional em cirurgias.
     */
    Page<EquipeCirurgica> findByProfissionalIdOrderByCreatedAtDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todas as participações de um profissional em cirurgias por função.
     */
    Page<EquipeCirurgica> findByProfissionalIdAndFuncaoOrderByCreatedAtDesc(
            UUID profissionalId, String funcao, Pageable pageable);

    /**
     * Busca todos os membros de equipe de um tenant.
     */
    Page<EquipeCirurgica> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}

