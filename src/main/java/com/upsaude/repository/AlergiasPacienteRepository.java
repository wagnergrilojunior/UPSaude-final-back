package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.entity.Tenant;

public interface AlergiasPacienteRepository extends JpaRepository<AlergiasPaciente, UUID> {

    Page<AlergiasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<AlergiasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    Page<AlergiasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
