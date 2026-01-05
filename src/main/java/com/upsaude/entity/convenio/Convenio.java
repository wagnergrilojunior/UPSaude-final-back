package com.upsaude.entity.convenio;
import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
import com.upsaude.util.converter.ModalidadeConvenioEnumConverter;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import com.upsaude.util.converter.TipoConvenioEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "convenios", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_convenios_cnpj_tenant", columnNames = {"cnpj", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_convenio_nome", columnList = "nome"),
           @Index(name = "idx_convenio_cnpj", columnList = "cnpj"),
           @Index(name = "idx_convenio_tipo", columnList = "tipo"),
           @Index(name = "idx_convenio_modalidade", columnList = "modalidade"),
           @Index(name = "idx_convenio_status", columnList = "status")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Convenio extends BaseEntity {

    @Column(name = "tenant_id", insertable = false, updatable = false)
    private UUID tenantId;

    @Column(name = "estabelecimento_id", insertable = false, updatable = false)
    private UUID estabelecimentoId;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;

    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;

    @Convert(converter = TipoConvenioEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    private TipoConvenioEnum tipo;

    @Convert(converter = ModalidadeConvenioEnumConverter.class)
    @Column(name = "modalidade")
    private ModalidadeConvenioEnum modalidade;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusAtivoEnum status = StatusAtivoEnum.ATIVO;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "rede_credenciada_nacional", nullable = false)
    private Boolean redeCredenciadaNacional = false;

    @Column(name = "rede_credenciada_regional", nullable = false)
    private Boolean redeCredenciadaRegional = false;

    @Column(name = "cobertura_obstetricia", nullable = false)
    private Boolean coberturaObstetricia = false;

    @Column(name = "habilitado_tiss", nullable = false)
    private Boolean habilitadoTiss = false;

    @Column(name = "sincronizar_ans", nullable = false)
    private Boolean sincronizarAns = false;

    @Column(name = "sincronizar_sus", nullable = false)
    private Boolean sincronizarSus = false;

    @Column(name = "sincronizar_tiss", nullable = false)
    private Boolean sincronizarTiss = false;

    public Convenio() {
    }
}
