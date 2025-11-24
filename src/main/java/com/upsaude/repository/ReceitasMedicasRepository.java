package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ReceitasMedicas;

public interface ReceitasMedicasRepository extends JpaRepository<ReceitasMedicas, UUID> {}
