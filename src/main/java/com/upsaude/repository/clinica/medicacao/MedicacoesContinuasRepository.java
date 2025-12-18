package com.upsaude.repository.clinica.medicacao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.clinica.medicacao.MedicacoesContinuas;

public interface MedicacoesContinuasRepository extends JpaRepository<MedicacoesContinuas, UUID> {
}
