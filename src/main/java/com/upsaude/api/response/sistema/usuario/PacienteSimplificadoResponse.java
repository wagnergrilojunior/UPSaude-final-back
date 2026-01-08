package com.upsaude.api.response.sistema.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteSimplificadoResponse {
    private UUID id;
    private String nome;
    private String email;
}
