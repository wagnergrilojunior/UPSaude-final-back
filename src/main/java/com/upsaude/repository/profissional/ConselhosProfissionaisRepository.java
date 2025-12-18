package com.upsaude.repository.profissional;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.profissional.ConselhosProfissionais;

public interface ConselhosProfissionaisRepository extends JpaRepository<ConselhosProfissionais, UUID> {
}
