package com.upsaude.api.response;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaInfoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID tenantId;
    private String tenantNome;
    private String tenantSlug;
    private UUID userId;
    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private Boolean adminTenant; // Se true, tem acesso total ao tenant
    private String nomeExibicao;
    private String user; // Username para login alternativo
    private String fotoUrl; // URL da foto no Supabase Storage
    
    // Lista de estabelecimentos vinculados (vazio se adminTenant=true)
    @lombok.Builder.Default
    private List<EstabelecimentoVinculoResponse> estabelecimentos = new ArrayList<>();
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoVinculoResponse {
        private UUID estabelecimentoId;
        private String estabelecimentoNome;
        private TipoUsuarioSistemaEnum tipoUsuario; // Tipo de acesso neste estabelecimento
    }
}

