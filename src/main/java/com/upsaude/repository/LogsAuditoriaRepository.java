package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.LogsAuditoria;

public interface LogsAuditoriaRepository extends JpaRepository<LogsAuditoria, UUID> {}
