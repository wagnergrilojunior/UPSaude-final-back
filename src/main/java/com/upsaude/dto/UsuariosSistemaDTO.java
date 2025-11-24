package com.upsaude.dto;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaDTO {
    private UUID id;
    private UUID userId;
    private UUID estabelecimentoId;
    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private TipoUsuarioSistemaEnum tipoUsuario;
    private String nomeExibicao;
    private Boolean active;
}

