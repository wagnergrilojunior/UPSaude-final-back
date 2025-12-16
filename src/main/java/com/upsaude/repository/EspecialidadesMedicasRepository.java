package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EspecialidadesMedicas;

public interface EspecialidadesMedicasRepository extends JpaRepository<EspecialidadesMedicas, UUID> {
}
