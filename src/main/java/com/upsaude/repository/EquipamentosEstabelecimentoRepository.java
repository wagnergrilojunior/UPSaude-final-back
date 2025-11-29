package com.upsaude.repository;

import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusManutencaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a EquipamentosEstabelecimento.
 *
 * @author UPSaúde
 */
@Repository
public interface EquipamentosEstabelecimentoRepository extends JpaRepository<EquipamentosEstabelecimento, UUID> {

    /**
     * Busca todos os equipamentos de um estabelecimento, ordenados por nome do equipamento.
     */
    Page<EquipamentosEstabelecimento> findByEstabelecimentoIdOrderByEquipamentoIdAsc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca equipamento por número de série em um estabelecimento.
     */
    Optional<EquipamentosEstabelecimento> findByEstabelecimentoIdAndNumeroSerie(
            UUID estabelecimentoId, String numeroSerie);

    /**
     * Busca todos os equipamentos de um estabelecimento por status de manutenção.
     */
    Page<EquipamentosEstabelecimento> findByEstabelecimentoIdAndStatusManutencaoOrderByEquipamentoIdAsc(
            UUID estabelecimentoId, StatusManutencaoEnum statusManutencao, Pageable pageable);

    /**
     * Busca todos os equipamentos ativos de um estabelecimento.
     */
    List<EquipamentosEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByEquipamentoIdAsc(
            UUID estabelecimentoId);

    /**
     * Busca todos os equipamentos de um tenant.
     */
    Page<EquipamentosEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}

