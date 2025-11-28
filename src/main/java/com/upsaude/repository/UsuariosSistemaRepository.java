package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuariosSistema;

public interface UsuariosSistemaRepository extends JpaRepository<UsuariosSistema, UUID> {
    
    /**
     * Busca todos os usuários do sistema de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de usuários do estabelecimento
     */
    Page<UsuariosSistema> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os usuários do sistema de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de usuários do tenant
     */
    Page<UsuariosSistema> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os usuários do sistema de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de usuários
     */
    Page<UsuariosSistema> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
