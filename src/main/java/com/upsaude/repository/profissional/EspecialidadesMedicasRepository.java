package com.upsaude.repository.profissional;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.profissional.EspecialidadesMedicas;

public interface EspecialidadesMedicasRepository extends JpaRepository<EspecialidadesMedicas, UUID> {
}
