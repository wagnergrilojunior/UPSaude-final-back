package com.upsaude.repository.clinica.cirurgia;

import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipeCirurgicaMedicoRepository extends JpaRepository<EquipeCirurgicaMedico, UUID> {
}

