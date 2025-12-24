package com.upsaude.api.response.sistema.usuario;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UsuarioSistemaInfoResponse {

    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID userId;

    private UUID profissionalSaudeId;
    private UUID medicoId;
    private UUID pacienteId;
    private String tipoVinculo;

    private String nomeExibicao;
    private String username;
    private String fotoUrl;

    private Boolean adminTenant;

    private UUID tenantId;
    private String tenantNome;
    private String tenantSlug;
    private Boolean tenantAtivo;
    private String tenantCnes;

    @lombok.Builder.Default
    private List<EstabelecimentoVinculoResponse> estabelecimentos = new ArrayList<>();

    @Data
    @Builder
    @AllArgsConstructor
    public static class EstabelecimentoVinculoResponse {
        private UUID id;
        private UUID estabelecimentoId;
        private String estabelecimentoNome;
        private String estabelecimentoCnes;
        private Boolean estabelecimentoAtivo;
        private TipoUsuarioSistemaEnum tipoUsuario;
    }
}
