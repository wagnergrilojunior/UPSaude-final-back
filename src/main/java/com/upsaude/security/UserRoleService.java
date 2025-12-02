package com.upsaude.security;

import com.upsaude.entity.Papeis;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.UsuariosPerfis;
import com.upsaude.repository.TenantRepository;
import com.upsaude.repository.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.UsuariosPerfisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço responsável por buscar e combinar roles do Supabase com papéis do sistema.
 * 
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UsuariosPerfisRepository usuariosPerfisRepository;
    private final UsuarioEstabelecimentoRepository usuarioEstabelecimentoRepository;
    private final TenantRepository tenantRepository;

    /**
     * Busca todas as authorities (roles) de um usuário, combinando:
     * 1. Role do Supabase (RBAC do Supabase)
     * 2. Papéis da tabela papeis (RBAC do sistema)
     * 
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @return lista de authorities do Spring Security
     */
    public List<GrantedAuthority> getUserAuthorities(UUID userId) {
        Set<String> authoritiesSet = new HashSet<>();
        
        // 1. Buscar papéis do sistema (tabela papeis) através de usuarios_perfis
        List<UsuariosPerfis> usuariosPerfis = usuariosPerfisRepository.findByUsuarioUserId(userId);
        
        for (UsuariosPerfis up : usuariosPerfis) {
            Papeis papel = up.getPapel();
            if (papel != null && papel.getActive() != null && papel.getActive()) {
                // Adiciona o slug do papel como authority (ex: ROLE_ADMIN, ROLE_MEDICO)
                String slug = papel.getSlug();
                if (slug != null && !slug.isEmpty()) {
                    authoritiesSet.add("ROLE_" + slug.toUpperCase());
                    log.debug("Papel do sistema adicionado: ROLE_{}", slug.toUpperCase());
                }
            }
        }
        
        // 2. Converter para lista de GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String authority : authoritiesSet) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        
        log.debug("Total de authorities encontradas para usuário {}: {}", userId, authorities.size());
        return authorities;
    }

    /**
     * Busca todas as authorities (roles) de um usuário para um tenant específico.
     * 
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @param tenantId ID do tenant
     * @return lista de authorities do Spring Security
     */
    public List<GrantedAuthority> getUserAuthoritiesForTenant(@NonNull UUID userId, @NonNull UUID tenantId) {
        Set<String> authoritiesSet = new HashSet<>();
        
        // Buscar tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElse(null);
        
        if (tenant == null) {
            log.warn("Tenant não encontrado: {}", tenantId);
            return new ArrayList<>();
        }
        
        // 1. Buscar papéis do sistema para o tenant específico
        List<UsuariosPerfis> usuariosPerfis = usuariosPerfisRepository.findByUsuarioUserIdAndTenant(userId, tenant);
        
        for (UsuariosPerfis up : usuariosPerfis) {
            Papeis papel = up.getPapel();
            if (papel != null && papel.getActive() != null && papel.getActive()) {
                String slug = papel.getSlug();
                if (slug != null && !slug.isEmpty()) {
                    authoritiesSet.add("ROLE_" + slug.toUpperCase());
                    log.debug("Papel do sistema adicionado para tenant {}: ROLE_{}", tenantId, slug.toUpperCase());
                }
            }
        }
        
        // 2. Converter para lista de GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String authority : authoritiesSet) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        
        log.debug("Total de authorities encontradas para usuário {} no tenant {}: {}", userId, tenantId, authorities.size());
        return authorities;
    }

    /**
     * Busca os IDs dos estabelecimentos que o usuário tem acesso.
     * 
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @return lista de IDs dos estabelecimentos
     */
    public List<UUID> getUserEstabelecimentosIds(UUID userId) {
        List<UsuarioEstabelecimento> estabelecimentos = usuarioEstabelecimentoRepository.findByUsuarioUserId(userId);
        return estabelecimentos.stream()
                .map(ue -> ue.getEstabelecimento().getId())
                .collect(Collectors.toList());
    }
}

