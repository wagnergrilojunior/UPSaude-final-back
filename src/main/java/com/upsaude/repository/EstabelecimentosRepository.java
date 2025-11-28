package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;

public interface EstabelecimentosRepository extends JpaRepository<Estabelecimentos, UUID> {
    
    /**
     * Busca todos os estabelecimentos de um tenant.
     *
     * @param tenant tenant
     * @return lista de estabelecimentos do tenant
     */
    List<Estabelecimentos> findByTenant(Tenant tenant);

    /**
     * Busca todos os estabelecimentos de um tenant, paginados.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de estabelecimentos do tenant
     */
    Page<Estabelecimentos> findByTenant(Tenant tenant, Pageable pageable);
}
