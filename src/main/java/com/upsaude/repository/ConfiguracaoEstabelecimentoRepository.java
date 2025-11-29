package com.upsaude.repository;

import com.upsaude.entity.ConfiguracaoEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a ConfiguracaoEstabelecimento.
 *
 * @author UPSaúde
 */
@Repository
public interface ConfiguracaoEstabelecimentoRepository extends JpaRepository<ConfiguracaoEstabelecimento, UUID> {

    /**
     * Busca a configuração de um estabelecimento (única por estabelecimento).
     */
    Optional<ConfiguracaoEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId);

    /**
     * Busca a configuração de um estabelecimento (única por estabelecimento).
     */
    Optional<ConfiguracaoEstabelecimento> findByEstabelecimento(Estabelecimentos estabelecimento);

    /**
     * Verifica se existe configuração para um estabelecimento.
     */
    boolean existsByEstabelecimentoId(UUID estabelecimentoId);

    /**
     * Busca todas as configurações de um tenant.
     */
    Page<ConfiguracaoEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}

