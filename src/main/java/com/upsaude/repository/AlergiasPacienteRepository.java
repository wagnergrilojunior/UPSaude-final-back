package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.AlergiasPaciente;

public interface AlergiasPacienteRepository extends JpaRepository<AlergiasPaciente, UUID> {}
