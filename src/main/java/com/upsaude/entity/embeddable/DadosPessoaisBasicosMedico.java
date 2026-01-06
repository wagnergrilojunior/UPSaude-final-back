package com.upsaude.entity.embeddable;

import com.upsaude.enums.SexoEnum;
import com.upsaude.util.converter.SexoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosPessoaisBasicosMedico {

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Column(name = "nome_social", length = 255)
    private String nomeSocial;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Convert(converter = SexoEnumConverter.class)
    @Column(name = "sexo")
    private SexoEnum sexo;
}
