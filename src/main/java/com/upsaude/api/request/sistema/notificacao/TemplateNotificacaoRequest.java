package com.upsaude.api.request.sistema.notificacao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.util.converter.CanalNotificacaoEnumDeserializer;
import com.upsaude.util.converter.TipoNotificacaoEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de template notificação")
public class TemplateNotificacaoRequest {
    private UUID estabelecimento;

    @NotBlank(message = "Nome do template é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    private String descricao;

    @NotNull(message = "Tipo de notificação é obrigatório")
    @JsonDeserialize(using = TipoNotificacaoEnumDeserializer.class)
    private TipoNotificacaoEnum tipoNotificacao;

    @NotNull(message = "Canal é obrigatório")
    @JsonDeserialize(using = CanalNotificacaoEnumDeserializer.class)
    private CanalNotificacaoEnum canal;

    @Size(max = 500, message = "Assunto deve ter no máximo 500 caracteres")
    private String assunto;

    @NotBlank(message = "Mensagem do template é obrigatória")
    private String mensagem;
    private String variaveisDisponiveis;
    private String exemploMensagem;
    private Integer horarioEnvioPrevistoHoras;
    private Boolean permiteEdicao;
    private Integer ordemPrioridade;
    private Boolean enviaAutomaticamente;
    private String condicoesEnvioJson;
    private String observacoes;
}
