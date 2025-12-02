package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuariosPerfis;

public interface UsuariosPerfisRepository extends JpaRepository<UsuariosPerfis, UUID> {
    
    /**
     * Busca todos os vínculos de usuários e perfis de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos do estabelecimento
     */
    Page<UsuariosPerfis> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os vínculos de usuários e perfis de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos do tenant
     */
    Page<UsuariosPerfis> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os vínculos de usuários e perfis de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<UsuariosPerfis> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Busca todos os papéis de um usuário pelo userId do Supabase.
     * Retorna os vínculos de usuários e perfis ativos para o usuário especificado.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @return lista de vínculos usuário-perfil
     */
    @Query("SELECT up FROM UsuariosPerfis up " +
           "JOIN up.usuario u " +
           "WHERE u.userId = :userId AND up.active = true")
    List<UsuariosPerfis> findByUsuarioUserId(@Param("userId") UUID userId);

    /**
     * Busca todos os papéis de um usuário pelo userId do Supabase e tenant.
     * Retorna os vínculos de usuários e perfis ativos para o usuário e tenant especificados.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @param tenant tenant
     * @return lista de vínculos usuário-perfil
     */
    @Query("SELECT up FROM UsuariosPerfis up " +
           "JOIN up.usuario u " +
           "WHERE u.userId = :userId AND up.tenant = :tenant AND up.active = true")
    List<UsuariosPerfis> findByUsuarioUserIdAndTenant(@Param("userId") UUID userId, @Param("tenant") Tenant tenant);

    /**
     * Busca todos os perfis de um usuário para um estabelecimento específico.
     * Retorna os vínculos de usuários e perfis ativos para o usuário e estabelecimento especificados.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @param estabelecimentoId ID do estabelecimento
     * @return lista de vínculos usuário-perfil para o estabelecimento
     */
    @Query("SELECT up FROM UsuariosPerfis up " +
           "JOIN up.usuario u " +
           "WHERE u.userId = :userId AND up.estabelecimento.id = :estabelecimentoId AND up.active = true")
    List<UsuariosPerfis> findByUsuarioUserIdAndEstabelecimentoId(@Param("userId") UUID userId, @Param("estabelecimentoId") UUID estabelecimentoId);
}
