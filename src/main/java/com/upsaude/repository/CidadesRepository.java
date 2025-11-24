package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Cidades;

public interface CidadesRepository extends JpaRepository<Cidades, UUID> {}
