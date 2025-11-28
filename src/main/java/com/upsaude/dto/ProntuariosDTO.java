package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuariosDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private String tipoRegistro;
    private String conteudo;
    private UUID criadoPor;
    private Boolean active;
}

