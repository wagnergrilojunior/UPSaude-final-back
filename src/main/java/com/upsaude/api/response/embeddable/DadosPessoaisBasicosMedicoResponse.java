package com.upsaude.api.response.embeddable;

import com.upsaude.enums.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosPessoaisBasicosMedicoResponse {
    private String nomeCompleto;
    private String nomeSocial;
    private LocalDate dataNascimento;
    private SexoEnum sexo;
}
