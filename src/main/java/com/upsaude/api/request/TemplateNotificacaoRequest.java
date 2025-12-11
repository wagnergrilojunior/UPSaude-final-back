package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.util.converter.CanalNotificacaoEnumDeserializer;
import com.upsaude.util.converter.TipoNotificacaoEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateNotificacaoRequest {
    private UUID estabelecimento;
    private String nome;
    private String descricao;
    @JsonDeserialize(using = TipoNotificacaoEnumDeserializer.class)
    private TipoNotificacaoEnum tipoNotificacao;

    @JsonDeserialize(using = CanalNotificacaoEnumDeserializer.class)
    private CanalNotificacaoEnum canal;
    private String assunto;
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
