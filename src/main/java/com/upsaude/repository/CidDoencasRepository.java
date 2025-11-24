package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CidDoencas;

public interface CidDoencasRepository extends JpaRepository<CidDoencas, UUID> {}
