package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.VisitasDomiciliares;

public interface VisitasDomiciliaresRepository extends JpaRepository<VisitasDomiciliares, UUID> {}
