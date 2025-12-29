package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoAlergiaEnum;
import com.upsaude.util.StringNormalizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Entidade representando alergia do paciente.
 * Alergia é informação clínica declarada do paciente, não diagnóstico CID.
 * Modelo simples e alinhado ao e-SUS APS e práticas SUS/UPA/UBS.
 */
@Entity
@Table(name = "alergias_paciente", schema = "public",
       indexes = {
           @Index(name = "idx_alergia_paciente_paciente", columnList = "paciente_id"),
           @Index(name = "idx_alergia_paciente_ativo", columnList = "ativo"),
           @Index(name = "idx_alergia_paciente_data_registro", columnList = "data_registro"),
           @Index(name = "idx_alergia_paciente_paciente_tipo", columnList = "paciente_id, tipo"),
           @Index(name = "idx_alergia_paciente_paciente_substancia", columnList = "paciente_id, substancia")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AlergiaPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @NotBlank(message = "Substância é obrigatória")
    @Size(max = 255, message = "Substância deve ter no máximo 255 caracteres")
    @Column(name = "substancia", nullable = false, length = 255)
    private String substancia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    @NotNull(message = "Tipo de alergia é obrigatório")
    private TipoAlergiaEnum tipo;

    @Size(max = 500, message = "Reação deve ter no máximo 500 caracteres")
    @Column(name = "reacao", length = 500)
    private String reacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravidade", nullable = false, length = 50)
    @NotNull(message = "Gravidade é obrigatória")
    private SeveridadeAlergiaEnum gravidade;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @Column(name = "data_registro", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataRegistro;

    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = OffsetDateTime.now();
        }
        if (this.getActive() == null) {
            this.setActive(true);
        }
    }

    /**
     * Normaliza a substância para comparação (trim + lowercase + remover múltiplos espaços).
     * @return Substância normalizada
     */
    public String getSubstanciaNormalizada() {
        return StringNormalizer.normalizeSubstancia(this.substancia);
    }
}

