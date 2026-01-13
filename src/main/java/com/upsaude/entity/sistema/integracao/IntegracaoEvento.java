package com.upsaude.entity.sistema.integracao;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.SistemaIntegracaoEnum;
import com.upsaude.enums.StatusIntegracaoEventoEnum;
import com.upsaude.enums.TipoErroIntegracaoEnum;
import com.upsaude.enums.TipoRecursoIntegracaoEnum;
import com.upsaude.util.converter.SistemaIntegracaoEnumConverter;
import com.upsaude.util.converter.StatusIntegracaoEventoEnumConverter;
import com.upsaude.util.converter.TipoErroIntegracaoEnumConverter;
import com.upsaude.util.converter.TipoRecursoIntegracaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "integracao_eventos", schema = "public", indexes = {
        @Index(name = "idx_integracao_evento_entidade", columnList = "entidade_tipo,entidade_id"),
        @Index(name = "idx_integracao_evento_sistema_recurso", columnList = "sistema,recurso"),
        @Index(name = "idx_integracao_evento_status", columnList = "status"),
        @Index(name = "idx_integracao_evento_proxima_tentativa", columnList = "proxima_tentativa_em"),
        @Index(name = "idx_integracao_evento_versao", columnList = "entidade_tipo,entidade_id,sistema,recurso,versao"),
        @Index(name = "idx_integracao_evento_correlation", columnList = "correlation_id"),
        @Index(name = "idx_integracao_evento_tenant", columnList = "tenant_id"),
        @Index(name = "idx_integracao_evento_estabelecimento", columnList = "estabelecimento_id")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class IntegracaoEvento extends BaseEntity {

    @Convert(converter = TipoRecursoIntegracaoEnumConverter.class)
    @Column(name = "entidade_tipo", nullable = false, length = 50)
    private TipoRecursoIntegracaoEnum entidadeTipo;

    @Column(name = "entidade_id", nullable = false)
    private UUID entidadeId;

    @Convert(converter = SistemaIntegracaoEnumConverter.class)
    @Column(name = "sistema", nullable = false)
    private SistemaIntegracaoEnum sistema;

    @Column(name = "recurso", nullable = false, length = 100)
    private String recurso;

    @Column(name = "versao", nullable = false)
    private Integer versao = 1;

    @Convert(converter = StatusIntegracaoEventoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusIntegracaoEventoEnum status = StatusIntegracaoEventoEnum.PENDENTE;

    @Column(name = "tentativas", nullable = false)
    private Integer tentativas = 0;

    @Column(name = "proxima_tentativa_em")
    private OffsetDateTime proximaTentativaEm;

    @Column(name = "data_envio")
    private OffsetDateTime dataEnvio;

    @Column(name = "data_conclusao")
    private OffsetDateTime dataConclusao;

    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    @Column(name = "protocolo", length = 200)
    private String protocolo;

    @Convert(converter = TipoErroIntegracaoEnumConverter.class)
    @Column(name = "erro_tipo")
    private TipoErroIntegracaoEnum erroTipo;

    @Column(name = "erro_msg", columnDefinition = "TEXT")
    private String erroMsg;

    @Column(name = "payload_request", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadRequest;

    @Column(name = "payload_response", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadResponse;

    public void marcarProcessando() {
        this.status = StatusIntegracaoEventoEnum.PROCESSANDO;
        this.dataEnvio = OffsetDateTime.now();
        this.tentativas = (this.tentativas == null ? 0 : this.tentativas) + 1;
    }

    public void marcarSucesso(String protocolo, String payloadResponse) {
        this.status = StatusIntegracaoEventoEnum.SUCESSO;
        this.protocolo = protocolo;
        this.payloadResponse = payloadResponse;
        this.dataConclusao = OffsetDateTime.now();
    }

    public void marcarErro(TipoErroIntegracaoEnum tipoErro, String mensagemErro, String payloadResponse, OffsetDateTime proximaTentativaEm) {
        this.status = StatusIntegracaoEventoEnum.ERRO;
        this.erroTipo = tipoErro;
        this.erroMsg = mensagemErro;
        this.payloadResponse = payloadResponse;
        this.proximaTentativaEm = proximaTentativaEm;
        if (proximaTentativaEm == null) {
            this.dataConclusao = OffsetDateTime.now();
        }
    }

    public boolean podeRetentar() {
        if (this.status != StatusIntegracaoEventoEnum.ERRO) {
            return false;
        }
        if (this.erroTipo == TipoErroIntegracaoEnum.VALIDACAO) {
            return false;
        }
        if (this.tentativas == null || this.tentativas >= 5) {
            return false;
        }
        if (this.proximaTentativaEm == null) {
            return false;
        }
        return OffsetDateTime.now().isAfter(this.proximaTentativaEm) || OffsetDateTime.now().isEqual(this.proximaTentativaEm);
    }
}
