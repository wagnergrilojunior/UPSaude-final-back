package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Medicos;

public interface MedicosRepository extends JpaRepository<Medicos, UUID> {}
