package com.upsaude.api.response;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Informações completas do usuário do sistema para incluir no LoginResponse.
 * Inclui dados de usuarios_sistema e estabelecimentos vinculados com seus tipos de acesso.
 */
@Data
@Builder
@AllArgsConstructor
public class UsuarioSistemaInfoResponse {
    // ========== DADOS DO USUARIO_SISTEMA ==========
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID userId;
    
    // Vínculos do usuário
    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private String tipoVinculo; // MEDICO, PROFISSIONAL, PACIENTE, OUTROS
    
    // Dados de exibição
    private String nomeExibicao;
    private String username; // Campo para login alternativo
    private String fotoUrl; // URL da foto no Supabase Storage
    
    // Permissões
    private Boolean adminTenant; // Se true, tem acesso total ao tenant
    
    // ========== DADOS DO TENANT ==========
    private UUID tenantId;
    private String tenantNome;
    private String tenantSlug;
    private Boolean tenantAtivo;
    private String tenantCnes;
    
    // ========== ESTABELECIMENTOS VINCULADOS ==========
    // Lista de estabelecimentos vinculados (vazio se adminTenant=true)
    @lombok.Builder.Default
    private List<EstabelecimentoVinculoResponse> estabelecimentos = new ArrayList<>();
    
    /**
     * Informações de vínculo com estabelecimento
     */
    @Data
    @Builder
    @AllArgsConstructor
    public static class EstabelecimentoVinculoResponse {
        private UUID id; // ID do vínculo
        private UUID estabelecimentoId;
        private String estabelecimentoNome;
        private String estabelecimentoCnes;
        private Boolean estabelecimentoAtivo;
        private TipoUsuarioSistemaEnum tipoUsuario; // Tipo de acesso neste estabelecimento
    }
}

