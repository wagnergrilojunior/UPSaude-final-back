package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;

public interface EstabelecimentosRepository extends JpaRepository<Estabelecimentos, UUID> {

    List<Estabelecimentos> findByTenant(Tenant tenant);

    Page<Estabelecimentos> findByTenant(Tenant tenant, Pageable pageable);

    Optional<Estabelecimentos> findByCnpjAndTenant(String cnpj, Tenant tenant);

    Optional<Estabelecimentos> findByCodigoCnesAndTenant(String codigoCnes, Tenant tenant);
}
