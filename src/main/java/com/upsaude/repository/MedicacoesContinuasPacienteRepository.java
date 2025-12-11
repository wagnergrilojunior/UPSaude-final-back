package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacoesContinuasPaciente;
import com.upsaude.entity.Tenant;

public interface MedicacoesContinuasPacienteRepository extends JpaRepository<MedicacoesContinuasPaciente, UUID> {

    Page<MedicacoesContinuasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<MedicacoesContinuasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    Page<MedicacoesContinuasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
