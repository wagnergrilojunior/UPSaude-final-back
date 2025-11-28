package com.upsaude.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.Vacinas;

public interface VacinasRepository extends JpaRepository<Vacinas, UUID> {
    
    /**
     * Busca todas as vacinas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vacinas do estabelecimento
     */
    Page<Vacinas> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as vacinas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vacinas do tenant
     */
    Page<Vacinas> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as vacinas de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vacinas
     */
    Page<Vacinas> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
