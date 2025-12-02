package com.upsaude.dto;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaDTO {
    private UUID id;
    private UUID userId;
    private UUID tenantId; // Tenant agora é campo direto
    private List<UUID> estabelecimentosIds; // Alterado para lista de estabelecimentos
    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private TipoUsuarioSistemaEnum tipoUsuario;
    private String nomeExibicao;
    private String user; // Username para login alternativo
    private String fotoUrl; // URL da foto no Supabase Storage
    private Boolean active;
}

