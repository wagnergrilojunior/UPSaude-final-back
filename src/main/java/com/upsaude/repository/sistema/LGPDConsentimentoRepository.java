package com.upsaude.repository.sistema;

import com.upsaude.entity.sistema.LGPDConsentimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LGPDConsentimentoRepository extends JpaRepository<LGPDConsentimento, UUID> {

    @Query("SELECT l FROM LGPDConsentimento l WHERE l.id = :id AND l.tenant.id = :tenantId")
    Optional<LGPDConsentimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LGPDConsentimento l WHERE l.tenant.id = :tenantId")
    Page<LGPDConsentimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LGPDConsentimento l WHERE l.paciente.id = :pacienteId AND l.tenant.id = :tenantId")
    Optional<LGPDConsentimento> findByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LGPDConsentimento l WHERE l.estabelecimento.id = :estabelecimentoId AND l.tenant.id = :tenantId")
    Page<LGPDConsentimento> findByEstabelecimentoIdAndTenantId(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);
}
