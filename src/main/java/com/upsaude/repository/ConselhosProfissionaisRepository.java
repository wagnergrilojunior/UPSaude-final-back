package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConselhosProfissionais;

public interface ConselhosProfissionaisRepository extends JpaRepository<ConselhosProfissionais, UUID> {}
