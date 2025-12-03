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
    
    /**
     * Busca todos os usuários do sistema de um estabelecimento.
     * Agora usa a tabela usuarios_estabelecimentos para buscar os vínculos.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de usuários do estabelecimento
     */
    @Query("SELECT DISTINCT u FROM UsuariosSistema u " +
           "JOIN u.estabelecimentosVinculados ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId AND ve.active = true")
    Page<UsuariosSistema> findByEstabelecimentoId(@Param("estabelecimentoId") UUID estabelecimentoId, Pageable pageable);

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
     * Agora usa a tabela usuarios_estabelecimentos para buscar os vínculos.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de usuários
     */
    @Query("SELECT DISTINCT u FROM UsuariosSistema u " +
           "JOIN u.estabelecimentosVinculados ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId " +
           "AND u.tenant = :tenant AND ve.active = true")
    Page<UsuariosSistema> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenant") Tenant tenant, Pageable pageable);
    
    /**
     * Busca um usuário do sistema pelo userId do Supabase.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @return usuário do sistema encontrado
     */
    @Query("SELECT u FROM UsuariosSistema u WHERE u.userId = :userId")
    java.util.Optional<UsuariosSistema> findByUserId(@Param("userId") UUID userId);

    /**
     * Busca um usuário do sistema pelo campo 'username'.
     *
     * @param username Username do usuário
     * @return usuário do sistema encontrado
     */
    @Query("SELECT u FROM UsuariosSistema u WHERE u.username = :username AND u.active = true")
    java.util.Optional<UsuariosSistema> findByUsername(@Param("username") String username);
}
