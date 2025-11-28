package com.upsaude.entity.embeddable;

import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.util.converter.EstadoCivilEnumConverter;
import com.upsaude.util.converter.SexoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe embeddable para dados pessoais do médico.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosPessoaisMedico {

    @Size(max = 255, message = "Nome social deve ter no máximo 255 caracteres")
    @Column(name = "nome_social", length = 255)
    private String nomeSocial; // Nome social (se diferente do nome completo)

    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    @Column(name = "cpf", length = 11)
    private String cpf;

    @Convert(converter = SexoEnumConverter.class)
    @Column(name = "sexo")
    private SexoEnum sexo;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Convert(converter = EstadoCivilEnumConverter.class)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    @Column(name = "rg", length = 20)
    private String rg;

    @Size(max = 10, message = "Órgão emissor RG deve ter no máximo 10 caracteres")
    @Column(name = "orgao_emissor_rg", length = 10)
    private String orgaoEmissorRg; // Ex: SSP, IFP, etc.

    @Size(max = 2, message = "UF emissor RG deve ter 2 caracteres")
    @Column(name = "uf_emissor_rg", length = 2)
    private String ufEmissorRg;

    @Size(max = 20, message = "Nacionalidade deve ter no máximo 20 caracteres")
    @Column(name = "nacionalidade", length = 20)
    private String nacionalidade;

    @Size(max = 50, message = "Naturalidade deve ter no máximo 50 caracteres")
    @Column(name = "naturalidade", length = 50)
    private String naturalidade;
}

