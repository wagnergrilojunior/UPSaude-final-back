package com.upsaude.api.request.embeddable;

import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.SexoEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class DadosPessoaisMedicoRequest {
    @Size(max = 255, message = "Nome social deve ter no máximo 255 caracteres")
    private String nomeSocial;
    
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;
    
    private SexoEnum sexo;
    
    private LocalDate dataNascimento;
    
    private EstadoCivilEnum estadoCivil;
    
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;
    
    @Size(max = 10, message = "Órgão emissor RG deve ter no máximo 10 caracteres")
    private String orgaoEmissorRg;
    
    @Size(max = 2, message = "UF emissor RG deve ter 2 caracteres")
    private String ufEmissorRg;
    
    @Size(max = 20, message = "Nacionalidade deve ter no máximo 20 caracteres")
    private String nacionalidade;
    
    @Size(max = 50, message = "Naturalidade deve ter no máximo 50 caracteres")
    private String naturalidade;
}
