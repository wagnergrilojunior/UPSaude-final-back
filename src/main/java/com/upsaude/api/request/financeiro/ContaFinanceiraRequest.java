package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de conta financeira (caixa/banco)")
public class ContaFinanceiraRequest {

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 20, message = "Tipo deve ter no máximo 20 caracteres")
    private String tipo; // CAIXA | BANCO

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 3, message = "Moeda deve ter no máximo 3 caracteres")
    private String moeda;

    @Size(max = 10, message = "Código do banco deve ter no máximo 10 caracteres")
    private String bancoCodigo;

    @Size(max = 20, message = "Agência deve ter no máximo 20 caracteres")
    private String agencia;

    @Size(max = 50, message = "Número da conta deve ter no máximo 50 caracteres")
    private String numeroConta;

    @Size(max = 255, message = "Chave PIX deve ter no máximo 255 caracteres")
    private String pixChave;

    private Boolean ativo;
}

