package com.upsaude.dto.embeddable;

import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.SexoEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosPessoaisMedicoDTO {
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
