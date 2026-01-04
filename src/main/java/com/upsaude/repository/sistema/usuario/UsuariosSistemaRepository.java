package com.upsaude.repository.sistema.usuario;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;

public interface UsuariosSistemaRepository extends JpaRepository<UsuariosSistema, UUID>, JpaSpecificationExecutor<UsuariosSistema> {

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

    @Query("SELECT u FROM UsuariosSistema u WHERE u.user.id = :userId")
    java.util.Optional<UsuariosSistema> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT u FROM UsuariosSistema u WHERE u.dadosIdentificacao.username = :username AND u.ativo = true")
    java.util.Optional<UsuariosSistema> findByUsername(@Param("username") String username);
}
