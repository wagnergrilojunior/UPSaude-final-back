package com.upsaude.repository.sistema.multitenancy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {}
