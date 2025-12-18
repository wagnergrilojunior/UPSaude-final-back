package com.upsaude.repository.estabelecimento.equipamento;

import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FabricantesEquipamentoRepository extends JpaRepository<FabricantesEquipamento, UUID> {

    Optional<FabricantesEquipamento> findByNomeIgnoreCase(String nome);

    Optional<FabricantesEquipamento> findByCnpj(String cnpj);

    Page<FabricantesEquipamento> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);
}
