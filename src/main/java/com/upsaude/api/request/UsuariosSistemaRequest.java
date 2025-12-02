package com.upsaude.api.request;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaRequest {
    private UUID userId;
    private UUID tenantId; // Tenant agora é campo direto
    private List<UUID> estabelecimentosIds; // Alterado para lista de estabelecimentos
    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private TipoUsuarioSistemaEnum tipoUsuario;
    private String nomeExibicao;
    private String user; // Username para login alternativo
}

