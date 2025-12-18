package com.upsaude.repository.clinica.medicacao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.clinica.medicacao.Medicacao;

public interface MedicacaoRepository extends JpaRepository<Medicacao, UUID> {
}
