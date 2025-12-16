package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.ContatoMedicoRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisMedicoRequest;
import com.upsaude.api.request.embeddable.FormacaoMedicoRequest;
import com.upsaude.api.request.embeddable.RegistroProfissionalMedicoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de médicos")
public class MedicosRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome completo")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;

    @Builder.Default
    private Set<UUID> especialidades = new HashSet<>();

    @Builder.Default
    private Set<UUID> estabelecimentos = new HashSet<>();

    @Valid
    private DadosPessoaisMedicoRequest dadosPessoais;

    @Valid
    private RegistroProfissionalMedicoRequest registroProfissional;

    @Valid
    private FormacaoMedicoRequest formacao;

    @Valid
    private ContatoMedicoRequest contato;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
