package com.upsaude.repository.sistema;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.sistema.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {}
