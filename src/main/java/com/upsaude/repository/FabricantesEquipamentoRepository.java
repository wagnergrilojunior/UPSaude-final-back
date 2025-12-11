package com.upsaude.repository;

import com.upsaude.entity.FabricantesEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FabricantesEquipamentoRepository extends JpaRepository<FabricantesEquipamento, UUID> {

    Optional<FabricantesEquipamento> findByCnpj(String cnpj);

    Page<FabricantesEquipamento> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);

    Page<FabricantesEquipamento> findAllByOrderByNomeAsc(Pageable pageable);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);

    boolean existsByCnpj(String cnpj);

    boolean existsByCnpjAndIdNot(String cnpj, UUID id);
}
