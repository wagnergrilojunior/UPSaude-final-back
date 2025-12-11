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

@Repository
public interface EquipamentosEstabelecimentoRepository extends JpaRepository<EquipamentosEstabelecimento, UUID> {

    Page<EquipamentosEstabelecimento> findByEstabelecimentoIdOrderByEquipamentoIdAsc(
            UUID estabelecimentoId, Pageable pageable);

    Optional<EquipamentosEstabelecimento> findByEstabelecimentoIdAndNumeroSerie(
            UUID estabelecimentoId, String numeroSerie);

    Page<EquipamentosEstabelecimento> findByEstabelecimentoIdAndStatusManutencaoOrderByEquipamentoIdAsc(
            UUID estabelecimentoId, StatusManutencaoEnum statusManutencao, Pageable pageable);

    List<EquipamentosEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByEquipamentoIdAsc(
            UUID estabelecimentoId);

    Page<EquipamentosEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}
