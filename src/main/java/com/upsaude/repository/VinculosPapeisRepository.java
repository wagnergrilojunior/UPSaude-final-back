package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.VinculosPapeis;

public interface VinculosPapeisRepository extends JpaRepository<VinculosPapeis, UUID> {}
