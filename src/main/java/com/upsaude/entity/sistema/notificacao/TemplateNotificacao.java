package com.upsaude.entity.sistema.notificacao;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.util.converter.CanalNotificacaoEnumConverter;
import com.upsaude.util.converter.TipoNotificacaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "templates_notificacao", schema = "public",
       indexes = {
           @Index(name = "idx_template_tipo", columnList = "tipo_notificacao"),
           @Index(name = "idx_template_canal", columnList = "canal"),
           @Index(name = "idx_template_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_template_ativo", columnList = "ativo")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateNotificacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    @Column(name = "nome", nullable = false, length = 255)
    @NotNull(message = "Nome do template é obrigatório")
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Convert(converter = TipoNotificacaoEnumConverter.class)
    @Column(name = "tipo_notificacao", nullable = false)
    @NotNull(message = "Tipo de notificação é obrigatório")
    private TipoNotificacaoEnum tipoNotificacao;

    @Convert(converter = CanalNotificacaoEnumConverter.class)
    @Column(name = "canal", nullable = false)
    @NotNull(message = "Canal é obrigatório")
    private CanalNotificacaoEnum canal;

    @Column(name = "assunto", length = 500)
    private String assunto;

    @Column(name = "mensagem", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Mensagem do template é obrigatória")
    private String mensagem;

    @Column(name = "variaveis_disponiveis", length = 1000)
    private String variaveisDisponiveis;

    @Column(name = "exemplo_mensagem", columnDefinition = "TEXT")
    private String exemploMensagem;

    @Column(name = "horario_envio_previsto_horas")
    private Integer horarioEnvioPrevistoHoras;

    @Column(name = "permite_edicao")
    private Boolean permiteEdicao;

    @Column(name = "ordem_prioridade")
    private Integer ordemPrioridade;

    @Column(name = "envia_automaticamente")
    private Boolean enviaAutomaticamente;

    @Column(name = "condicoes_envio_json", columnDefinition = "TEXT")
    private String condicoesEnvioJson;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
