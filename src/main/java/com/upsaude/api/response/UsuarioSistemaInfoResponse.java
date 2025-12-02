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
 * Inclui dados de usuarios_sistema, estabelecimentos vinculados e perfis por estabelecimento.
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
    private TipoUsuarioSistemaEnum tipoUsuario;
    private String nomeExibicao;
    private String user; // Username para login alternativo
    private String fotoUrl; // URL da foto no Supabase Storage
    
    // Lista de estabelecimentos vinculados com seus perfis
    private List<EstabelecimentoComPerfisResponse> estabelecimentos = new ArrayList<>();
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoComPerfisResponse {
        private UUID estabelecimentoId;
        private String estabelecimentoNome;
        private List<PerfilResponse> perfis = new ArrayList<>();
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerfilResponse {
        private UUID papelId;
        private String papelNome;
        private String papelSlug;
        private String escopo;
    }
}

