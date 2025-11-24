package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesMedicamento;

public interface FabricantesMedicamentoRepository extends JpaRepository<FabricantesMedicamento, UUID> {}
