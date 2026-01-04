package com.upsaude.entity.paciente.deficiencia;
import com.upsaude.entity.BaseEntityWithoutTenant;

import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.util.converter.TipoDeficienciaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "deficiencias", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Deficiencias extends BaseEntityWithoutTenant {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Convert(converter = TipoDeficienciaEnumConverter.class)
    @Column(name = "tipo_deficiencia")
    private TipoDeficienciaEnum tipoDeficiencia;

    @Column(name = "cid10_relacionado", length = 10)
    private String cid10Relacionado;

    @Column(name = "permanente", nullable = false)
    private Boolean permanente = false;

    @Column(name = "acompanhamento_continuo", nullable = false)
    private Boolean acompanhamentoContinuo = false;
}
