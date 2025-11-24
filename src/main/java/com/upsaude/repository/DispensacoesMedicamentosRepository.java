package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DispensacoesMedicamentos;

public interface DispensacoesMedicamentosRepository extends JpaRepository<DispensacoesMedicamentos, UUID> {}
