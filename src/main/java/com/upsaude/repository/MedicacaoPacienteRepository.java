package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacaoPaciente;

/**
 * Repositório para operações de banco de dados relacionadas a Medicações de Paciente.
 *
 * @author UPSaúde
 */
public interface MedicacaoPacienteRepository extends JpaRepository<MedicacaoPaciente, UUID> {}

