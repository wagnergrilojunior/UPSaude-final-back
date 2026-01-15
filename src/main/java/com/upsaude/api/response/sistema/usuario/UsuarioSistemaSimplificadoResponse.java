package com.upsaude.api.response.sistema.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaSimplificadoResponse {
    private UUID id;
    private String nomeExibicao;
    private String username;
}
