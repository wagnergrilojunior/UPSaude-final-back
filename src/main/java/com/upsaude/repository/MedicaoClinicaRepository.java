package com.upsaude.repository;

import com.upsaude.entity.MedicaoClinica;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicaoClinicaRepository extends JpaRepository<MedicaoClinica, UUID> {

    Page<MedicaoClinica> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    Page<MedicaoClinica> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    Page<MedicaoClinica> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);

    Page<MedicaoClinica> findByEstabelecimentoIdAndTenantOrderByDataHoraDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
