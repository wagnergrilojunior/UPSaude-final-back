package com.upsaude.api.response.sistema.notificacao;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;

import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateNotificacaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private String nome;
    private String descricao;
    private TipoNotificacaoEnum tipoNotificacao;
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
