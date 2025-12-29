package com.upsaude.api.request.sistema.usuario;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.api.request.embeddable.ConfiguracaoUsuarioRequest;
import com.upsaude.api.request.embeddable.DadosExibicaoUsuarioRequest;
import com.upsaude.api.request.embeddable.DadosIdentificacaoUsuarioRequest;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import com.upsaude.util.converter.TipoUsuarioSistemaEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de usuários sistema")
public class UsuariosSistemaRequest {

    @NotNull(message = "User ID é obrigatório")
    private UUID userId;

    private UUID profissionalSaude;
    private UUID medico;
    private UUID paciente;

    @NotNull(message = "Tenant é obrigatório")
    private UUID tenantId;

    @Valid
    private DadosIdentificacaoUsuarioRequest dadosIdentificacao;

    @Valid
    private DadosExibicaoUsuarioRequest dadosExibicao;

    @Valid
    private ConfiguracaoUsuarioRequest configuracao;

    @Builder.Default
    private List<EstabelecimentoVinculoRequest> estabelecimentos = new ArrayList<>();

    @Deprecated
    @Builder.Default
    private List<UUID> estabelecimentosIds = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoVinculoRequest {
        @NotNull(message = "Estabelecimento é obrigatório")
        private UUID estabelecimentoId;

        @JsonDeserialize(using = TipoUsuarioSistemaEnumDeserializer.class)
        private TipoUsuarioSistemaEnum tipoUsuario;
    }
}
