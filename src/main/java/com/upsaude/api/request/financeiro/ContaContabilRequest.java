package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de conta contábil")
public class ContaContabilRequest {

    @NotNull(message = "Plano de contas é obrigatório")
    private UUID planoContas;

    private UUID contaPai;

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Natureza é obrigatória")
    @Size(max = 20, message = "Natureza deve ter no máximo 20 caracteres")
    private String natureza; // RECEITA | DESPESA | ATIVO | PASSIVO | PL

    private Boolean aceitaLancamento;
    private Integer nivel;
    private Integer ordem;
}

