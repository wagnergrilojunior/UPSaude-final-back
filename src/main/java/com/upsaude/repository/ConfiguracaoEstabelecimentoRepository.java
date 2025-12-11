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

@Repository
public interface ConfiguracaoEstabelecimentoRepository extends JpaRepository<ConfiguracaoEstabelecimento, UUID> {

    Optional<ConfiguracaoEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId);

    Optional<ConfiguracaoEstabelecimento> findByEstabelecimento(Estabelecimentos estabelecimento);

    boolean existsByEstabelecimentoId(UUID estabelecimentoId);

    Page<ConfiguracaoEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}
