package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.entity.Tenant;

public interface PerfisUsuariosRepository extends JpaRepository<PerfisUsuarios, UUID> {
    
    /**
     * Busca todos os perfis de usuários de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de perfis do estabelecimento
     */
    Page<PerfisUsuarios> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os perfis de usuários de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de perfis do tenant
     */
    Page<PerfisUsuarios> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os perfis de usuários de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de perfis
     */
    Page<PerfisUsuarios> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
