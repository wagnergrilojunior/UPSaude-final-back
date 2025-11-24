package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Consultas;

public interface ConsultasRepository extends JpaRepository<Consultas, UUID> {}
