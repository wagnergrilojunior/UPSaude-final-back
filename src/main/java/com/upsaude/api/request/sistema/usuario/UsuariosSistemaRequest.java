package com.upsaude.api.request.sistema.usuario;
import com.upsaude.entity.sistema.multitenancy.Tenant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import com.upsaude.util.converter.TipoUsuarioSistemaEnumDeserializer;
import com.upsaude.validation.annotation.EmailValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de usuários sistema")
public class UsuariosSistemaRequest {
    private UUID userId;
    private UUID profissionalSaude;
    private UUID medico;
    private UUID paciente;
    @NotNull(message = "Tenant é obrigatório")
    private UUID tenantId;

    private Boolean adminTenant;

    @Size(max = 50, message = "Tipo vínculo deve ter no máximo 50 caracteres")
    private String tipoVinculo;

    @Size(max = 255, message = "Nome exibição deve ter no máximo 255 caracteres")
    private String nomeExibicao;

    @NotBlank(message = "Username é obrigatório")
    @Size(max = 100, message = "Username deve ter no máximo 100 caracteres")
    private String username;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String senha;

    @Size(max = 500, message = "URL da foto deve ter no máximo 500 caracteres")
    private String fotoUrl;

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
