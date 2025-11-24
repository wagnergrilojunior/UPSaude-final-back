package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Alergias;

public interface AlergiasRepository extends JpaRepository<Alergias, UUID> {}
