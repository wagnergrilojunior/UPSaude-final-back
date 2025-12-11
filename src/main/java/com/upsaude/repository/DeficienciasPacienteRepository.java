package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DeficienciasPaciente;
import com.upsaude.entity.Tenant;

public interface DeficienciasPacienteRepository extends JpaRepository<DeficienciasPaciente, UUID> {

    Page<DeficienciasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<DeficienciasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    Page<DeficienciasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
