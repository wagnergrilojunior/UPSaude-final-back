package com.upsaude.api.response.sistema.usuario;

import com.upsaude.api.response.embeddable.ConfiguracaoUsuarioResponse;
import com.upsaude.api.response.embeddable.DadosExibicaoUsuarioResponse;
import com.upsaude.api.response.embeddable.DadosIdentificacaoUsuarioResponse;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean ativo;
    private UUID userId;
    private ProfissionalSaudeSimplificadoResponse profissionalSaude;
    private MedicoSimplificadoResponse medico;
    private PacienteSimplificadoResponse paciente;
    private UsuarioSimplificadoResponse usuario;
    private DadosIdentificacaoUsuarioResponse dadosIdentificacao;
    private DadosExibicaoUsuarioResponse dadosExibicao;
    private ConfiguracaoUsuarioResponse configuracao;

    private UUID tenantId;
    private String tenantNome;
    private String tenantSlug;

    private TipoUsuarioSistemaEnum tipoUsuario;

    private Boolean usuarioConsorcio;

    @Builder.Default
    private List<EstabelecimentoVinculoSimples> estabelecimentosVinculados = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoVinculoSimples {
        private UUID id;
        private UUID estabelecimentoId;
        private String estabelecimentoNome;
        private TipoUsuarioSistemaEnum tipoUsuario;
        private Boolean active;
    }
}
