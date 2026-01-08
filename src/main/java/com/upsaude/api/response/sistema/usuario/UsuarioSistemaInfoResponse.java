package com.upsaude.api.response.sistema.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaInfoResponse {

    private UUID id;
    private UUID userId;

    private String nomeExibicao;
    private String username;
    private String fotoUrl;

    private Boolean adminTenant;

    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;

    private TenantSimplificadoResponse tenant;

    @lombok.Builder.Default
    private List<EstabelecimentoVinculoSimplificadoResponse> estabelecimentos = new ArrayList<>();
}
