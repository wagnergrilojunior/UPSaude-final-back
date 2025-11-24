package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.UsuariosSistema;

public interface UsuariosSistemaRepository extends JpaRepository<UsuariosSistema, UUID> {}
