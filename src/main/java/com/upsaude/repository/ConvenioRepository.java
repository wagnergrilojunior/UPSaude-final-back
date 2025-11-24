package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Convenio;

public interface ConvenioRepository extends JpaRepository<Convenio, UUID> {}
