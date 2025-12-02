package com.upsaude.api.response;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID userId;
    private UUID tenantId; // Tenant agora é campo direto
    private String tenantNome; // Nome do tenant
    private String tenantSlug; // Slug do tenant
    private List<UUID> estabelecimentosIds; // Alterado para lista de estabelecimentos
    private List<String> estabelecimentosNomes; // Adicionado para nomes dos estabelecimentos
    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private TipoUsuarioSistemaEnum tipoUsuario;
    private String nomeExibicao;
    private String user; // Username para login alternativo
    private String fotoUrl; // URL da foto no Supabase Storage
}

