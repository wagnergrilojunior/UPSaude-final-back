package com.upsaude.repository.paciente.alergia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.paciente.alergia.AlergiasPaciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface AlergiasPacienteRepository extends JpaRepository<AlergiasPaciente, UUID> {

    Page<AlergiasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<AlergiasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    Page<AlergiasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
