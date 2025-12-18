package com.upsaude.repository.referencia.geografico;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.referencia.geografico.Estados;

public interface EstadosRepository extends JpaRepository<Estados, UUID> {}

