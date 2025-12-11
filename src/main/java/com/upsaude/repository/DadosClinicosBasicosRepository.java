package com.upsaude.repository;

import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DadosClinicosBasicosRepository extends JpaRepository<DadosClinicosBasicos, UUID> {

    Optional<DadosClinicosBasicos> findByPacienteId(UUID pacienteId);

    Page<DadosClinicosBasicos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<DadosClinicosBasicos> findByTenant(Tenant tenant, Pageable pageable);

    Page<DadosClinicosBasicos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
