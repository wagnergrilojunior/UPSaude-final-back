package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EstoquesVacina;

public interface EstoquesVacinaRepository extends JpaRepository<EstoquesVacina, UUID> {}
