package com.upsaude.entity.cnes;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.util.converter.StatusSincronizacaoEnumConverter;
import com.upsaude.util.converter.TipoEntidadeCnesEnumConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cnes_sincronizacao", schema = "public",
       indexes = {
           @Index(name = "idx_cnes_sincronizacao_tipo_entidade", columnList = "tipo_entidade"),
           @Index(name = "idx_cnes_sincronizacao_entidade_id", columnList = "entidade_id"),
           @Index(name = "idx_cnes_sincronizacao_status", columnList = "status"),
           @Index(name = "idx_cnes_sincronizacao_data_sincronizacao", columnList = "data_sincronizacao"),
           @Index(name = "idx_cnes_sincronizacao_codigo_identificador", columnList = "codigo_identificador"),
           @Index(name = "idx_cnes_sincronizacao_estabelecimento", columnList = "estabelecimento_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CnesSincronizacao extends BaseEntity {

    @Convert(converter = TipoEntidadeCnesEnumConverter.class)
    @Column(name = "tipo_entidade", nullable = false)
    private TipoEntidadeCnesEnum tipoEntidade;

    @Column(name = "entidade_id")
    private UUID entidadeId;

    @Column(name = "codigo_identificador", length = 50)
    private String codigoIdentificador;

    @Column(name = "competencia", length = 6)
    private String competencia;

    @Convert(converter = StatusSincronizacaoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusSincronizacaoEnum status;

    @Column(name = "data_sincronizacao", nullable = false)
    private OffsetDateTime dataSincronizacao;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Column(name = "registros_inseridos")
    private Integer registrosInseridos = 0;

    @Column(name = "registros_atualizados")
    private Integer registrosAtualizados = 0;

    @Column(name = "registros_erro")
    private Integer registrosErro = 0;

    @Column(name = "mensagem_erro", columnDefinition = "TEXT")
    private String mensagemErro;

    @Column(name = "detalhes_erro", columnDefinition = "jsonb")
    private String detalhesErro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;
}

