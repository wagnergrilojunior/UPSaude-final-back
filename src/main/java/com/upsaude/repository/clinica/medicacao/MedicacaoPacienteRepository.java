package com.upsaude.repository.clinica.medicacao;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.clinica.medicacao.MedicacaoPaciente;
import com.upsaude.entity.sistema.Tenant;

public interface MedicacaoPacienteRepository extends JpaRepository<MedicacaoPaciente, UUID> {

    Page<MedicacaoPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<MedicacaoPaciente> findByTenant(Tenant tenant, Pageable pageable);

    Page<MedicacaoPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
