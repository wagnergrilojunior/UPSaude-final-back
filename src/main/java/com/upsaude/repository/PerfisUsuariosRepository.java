package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PerfisUsuarios;

public interface PerfisUsuariosRepository extends JpaRepository<PerfisUsuarios, UUID> {}
