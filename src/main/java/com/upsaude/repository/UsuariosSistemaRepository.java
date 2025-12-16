package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuariosSistema;

public interface UsuariosSistemaRepository extends JpaRepository<UsuariosSistema, UUID> {

    @Query("SELECT DISTINCT u FROM UsuariosSistema u " +
           "JOIN u.estabelecimentosVinculados ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId AND ve.active = true")
    Page<UsuariosSistema> findByEstabelecimentoId(@Param("estabelecimentoId") UUID estabelecimentoId, Pageable pageable);

    Page<UsuariosSistema> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT DISTINCT u FROM UsuariosSistema u " +
           "JOIN u.estabelecimentosVinculados ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId " +
           "AND u.tenant = :tenant AND ve.active = true")
    Page<UsuariosSistema> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenant") Tenant tenant, Pageable pageable);

    @Query("SELECT u FROM UsuariosSistema u WHERE u.userId = :userId")
    java.util.Optional<UsuariosSistema> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT u FROM UsuariosSistema u WHERE u.username = :username AND u.active = true")
    java.util.Optional<UsuariosSistema> findByUsername(@Param("username") String username);
}
