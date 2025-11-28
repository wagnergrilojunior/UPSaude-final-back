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

    /**
     * Busca todos os consentimentos LGPD de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de consentimentos do estabelecimento
     */
    Page<LGPDConsentimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os consentimentos LGPD de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de consentimentos do tenant
     */
    Page<LGPDConsentimento> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os consentimentos LGPD de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de consentimentos
     */
    Page<LGPDConsentimento> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

