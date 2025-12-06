package com.upsaude.api.response.embeddable;

import com.upsaude.enums.EstadoCivilEnum;
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
public class DadosPessoaisMedicoResponse {
    private String nomeSocial;
    private String cpf;
    private SexoEnum sexo;
    private LocalDate dataNascimento;
    private EstadoCivilEnum estadoCivil;
    private String rg;
    private String orgaoEmissorRg;
    private String ufEmissorRg;
    private String nacionalidade;
    private String naturalidade;
}
