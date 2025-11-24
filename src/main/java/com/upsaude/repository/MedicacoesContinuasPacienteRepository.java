package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacoesContinuasPaciente;

public interface MedicacoesContinuasPacienteRepository extends JpaRepository<MedicacoesContinuasPaciente, UUID> {}
