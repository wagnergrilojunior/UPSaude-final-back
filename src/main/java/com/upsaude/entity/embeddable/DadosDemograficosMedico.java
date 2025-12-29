package com.upsaude.entity.embeddable;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.util.converter.EscolaridadeEnumConverter;
import com.upsaude.util.converter.EstadoCivilEnumConverter;
import com.upsaude.util.converter.NacionalidadeEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosDemograficosMedico {

    @Convert(converter = EstadoCivilEnumConverter.class)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @Convert(converter = EscolaridadeEnumConverter.class)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    @Convert(converter = NacionalidadeEnumConverter.class)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    @Column(name = "naturalidade", length = 50)
    private String naturalidade;
}

