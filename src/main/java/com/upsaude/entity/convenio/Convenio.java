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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

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

    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;

    @Convert(converter = TipoConvenioEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo de convênio é obrigatório")
    private TipoConvenioEnum tipo;

    @Convert(converter = ModalidadeConvenioEnumConverter.class)
    @Column(name = "modalidade")
    private ModalidadeConvenioEnum modalidade;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "rede_credenciada_nacional", nullable = false)
    private Boolean redeCredenciadaNacional = false;

    @Column(name = "rede_credenciada_regional", nullable = false)
    private Boolean redeCredenciadaRegional = false;

    public Convenio() {
    }
}
