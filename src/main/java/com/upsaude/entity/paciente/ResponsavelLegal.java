package com.upsaude.entity.paciente;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.enums.TipoResponsavelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "responsaveis_legais", schema = "public",
       indexes = {
           @Index(name = "idx_responsaveis_legais_paciente", columnList = "paciente_id"),
           @Index(name = "idx_responsaveis_legais_cpf", columnList = "cpf")
       })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegal extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    @Column(name = "cpf", length = 14)
    private String cpf;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_responsavel")
    private TipoResponsavelEnum tipoResponsavel;

    @Column(name = "autorizacao_uso_dados_lgpd", nullable = false)
    private Boolean autorizacaoUsoDadosLGPD = false;

    @Column(name = "autorizacao_responsavel", nullable = false)
    private Boolean autorizacaoResponsavel = false;
}
