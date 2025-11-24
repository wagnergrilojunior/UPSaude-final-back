package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Exames;

public interface ExamesRepository extends JpaRepository<Exames, UUID> {}
