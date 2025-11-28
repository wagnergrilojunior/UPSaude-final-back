package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DeficienciasPaciente;

/**
 * Repositório para operações de banco de dados relacionadas a Deficiências de Paciente.
 *
 * @author UPSaúde
 */
public interface DeficienciasPacienteRepository extends JpaRepository<DeficienciasPaciente, UUID> {}

