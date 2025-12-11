package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesVacina;

public interface FabricantesVacinaRepository extends JpaRepository<FabricantesVacina, UUID> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);

    boolean existsByCnpj(String cnpj);

    boolean existsByCnpjAndIdNot(String cnpj, UUID id);
}
