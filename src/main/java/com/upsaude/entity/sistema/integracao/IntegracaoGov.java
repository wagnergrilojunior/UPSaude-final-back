package com.upsaude.entity.sistema.integracao;

import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.enums.SistemaIntegracaoEnum;
import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoCnsEnum;
import com.upsaude.util.converter.SistemaIntegracaoEnumConverter;
import com.upsaude.util.converter.StatusSincronizacaoEnumConverter;
import com.upsaude.util.converter.TipoCnsEnumConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "paciente_integracao_gov", schema = "public", indexes = {
        @Index(name = "idx_paciente_integracao_gov_paciente", columnList = "paciente_id"),
        @Index(name = "idx_paciente_integracao_gov_uuid_rnds", columnList = "uuid_rnds"),
        @Index(name = "idx_paciente_integracao_gov_id_integracao", columnList = "id_integracao_gov"),
        @Index(name = "idx_paciente_integracao_gov_sistema", columnList = "sistema"),
        @Index(name = "idx_paciente_integracao_gov_status_sincronizacao", columnList = "status_sincronizacao"),
        @Index(name = "idx_paciente_integracao_gov_data_sincronizacao", columnList = "data_sincronizacao")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGov extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Convert(converter = SistemaIntegracaoEnumConverter.class)
    @Column(name = "sistema")
    private SistemaIntegracaoEnum sistema;

    @Column(name = "id_integracao_gov", length = 100)
    private String idIntegracaoGov;

    @Column(name = "uuid_rnds")
    private UUID uuidRnds;

    @Column(name = "versao_layout", length = 50)
    private String versaoLayout;

    @Column(name = "payload_bruto", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadBruto;

    @Column(name = "data_sincronizacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataSincronizacao;

    @Convert(converter = StatusSincronizacaoEnumConverter.class)
    @Column(name = "status_sincronizacao")
    private StatusSincronizacaoEnum statusSincronizacao;

    @Column(name = "erro_sincronizacao", columnDefinition = "TEXT")
    private String erroSincronizacao;

    @Column(name = "cns_validado", nullable = false)
    private Boolean cnsValidado = false;

    @Convert(converter = TipoCnsEnumConverter.class)
    @Column(name = "tipo_cns")
    private TipoCnsEnum tipoCns;

    @Column(name = "data_atualizacao_cns")
    private LocalDate dataAtualizacaoCns;

    @Column(name = "cartao_sus_ativo", nullable = false)
    private Boolean cartaoSusAtivo = true;

    @Column(name = "origem_cadastro", length = 30)
    private String origemCadastro;
}
