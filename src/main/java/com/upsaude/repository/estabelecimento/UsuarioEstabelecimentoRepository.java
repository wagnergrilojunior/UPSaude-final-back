package com.upsaude.repository.estabelecimento;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;

public interface UsuarioEstabelecimentoRepository extends JpaRepository<UsuarioEstabelecimento, UUID> {

    @Query("SELECT ue FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.active = true")
    List<UsuarioEstabelecimento> findByUsuarioUserId(@Param("userId") UUID userId);

    @Query("SELECT ue FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.tenant = :tenant AND ue.active = true")
    List<UsuarioEstabelecimento> findByUsuarioUserIdAndTenant(@Param("userId") UUID userId, @Param("tenant") Tenant tenant);

    Page<UsuarioEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<UsuarioEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT COUNT(ue) > 0 FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.estabelecimento.id = :estabelecimentoId AND ue.active = true")
    boolean existsByUsuarioUserIdAndEstabelecimentoId(@Param("userId") UUID userId, @Param("estabelecimentoId") UUID estabelecimentoId);

    @Query("SELECT ue FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.estabelecimento.id = :estabelecimentoId")
    java.util.Optional<UsuarioEstabelecimento> findByUsuarioUserIdAndEstabelecimentoId(@Param("userId") UUID userId, @Param("estabelecimentoId") UUID estabelecimentoId);
}
