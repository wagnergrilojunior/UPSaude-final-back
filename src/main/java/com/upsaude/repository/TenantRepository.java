package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {}
