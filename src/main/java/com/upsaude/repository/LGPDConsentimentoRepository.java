package com.upsaude.repository;

import com.upsaude.entity.LGPDConsentimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LGPDConsentimentoRepository extends JpaRepository<LGPDConsentimento, UUID> {

    Optional<LGPDConsentimento> findByPacienteId(UUID pacienteId);

    Page<LGPDConsentimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<LGPDConsentimento> findByTenant(Tenant tenant, Pageable pageable);

    Page<LGPDConsentimento> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
