package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesVacina;

public interface FabricantesVacinaRepository extends JpaRepository<FabricantesVacina, UUID> {}
