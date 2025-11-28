package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Endereco;
import com.upsaude.entity.Tenant;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    
    /**
     * Busca todos os endereços de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de endereços do estabelecimento
     */
    Page<Endereco> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os endereços de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de endereços do tenant
     */
    Page<Endereco> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os endereços de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de endereços
     */
    Page<Endereco> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
