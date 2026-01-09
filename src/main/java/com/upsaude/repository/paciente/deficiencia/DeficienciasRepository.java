package com.upsaude.repository.paciente.deficiencia;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.paciente.deficiencia.Deficiencias;

public interface DeficienciasRepository extends JpaRepository<Deficiencias, UUID> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);
}
