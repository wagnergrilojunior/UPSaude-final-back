package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuarioEstabelecimento;

public interface UsuarioEstabelecimentoRepository extends JpaRepository<UsuarioEstabelecimento, UUID> {
    
    /**
     * Busca todos os estabelecimentos vinculados a um usuário pelo userId do Supabase.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @return lista de vínculos usuário-estabelecimento ativos
     */
    @Query("SELECT ue FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.active = true")
    List<UsuarioEstabelecimento> findByUsuarioUserId(@Param("userId") UUID userId);

    /**
     * Busca todos os estabelecimentos vinculados a um usuário pelo userId do Supabase e tenant.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @param tenant tenant
     * @return lista de vínculos usuário-estabelecimento ativos
     */
    @Query("SELECT ue FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.tenant = :tenant AND ue.active = true")
    List<UsuarioEstabelecimento> findByUsuarioUserIdAndTenant(@Param("userId") UUID userId, @Param("tenant") Tenant tenant);

    /**
     * Busca todos os vínculos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos do estabelecimento
     */
    Page<UsuarioEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os vínculos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos do tenant
     */
    Page<UsuarioEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Verifica se um usuário tem acesso a um estabelecimento específico.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @param estabelecimentoId ID do estabelecimento
     * @return true se o usuário tem acesso ao estabelecimento
     */
    @Query("SELECT COUNT(ue) > 0 FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.estabelecimento.id = :estabelecimentoId AND ue.active = true")
    boolean existsByUsuarioUserIdAndEstabelecimentoId(@Param("userId") UUID userId, @Param("estabelecimentoId") UUID estabelecimentoId);

    /**
     * Busca um vínculo específico entre usuário e estabelecimento.
     *
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @param estabelecimentoId ID do estabelecimento
     * @return Optional com o vínculo encontrado
     */
    @Query("SELECT ue FROM UsuarioEstabelecimento ue " +
           "JOIN ue.usuario u " +
           "WHERE u.userId = :userId AND ue.estabelecimento.id = :estabelecimentoId")
    java.util.Optional<UsuarioEstabelecimento> findByUsuarioUserIdAndEstabelecimentoId(@Param("userId") UUID userId, @Param("estabelecimentoId") UUID estabelecimentoId);
}

