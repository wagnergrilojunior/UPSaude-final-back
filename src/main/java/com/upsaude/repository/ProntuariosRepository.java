package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Prontuarios;

public interface ProntuariosRepository extends JpaRepository<Prontuarios, UUID> {}
