package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ProfissionaisSaude;

public interface ProfissionaisSaudeRepository extends JpaRepository<ProfissionaisSaude, UUID> {}
