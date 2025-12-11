package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.Tenant;

public interface MedicacaoPacienteRepository extends JpaRepository<MedicacaoPaciente, UUID> {

    Page<MedicacaoPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<MedicacaoPaciente> findByTenant(Tenant tenant, Pageable pageable);

    Page<MedicacaoPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
