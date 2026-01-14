package com.upsaude.api.request.estabelecimento;

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
@Schema(description = "Dados de conta bancária do estabelecimento")
public class ContaBancariaEstabelecimentoRequest {

    @NotBlank(message = "Código do banco é obrigatório")
    @Size(max = 10, message = "Código do banco deve ter no máximo 10 caracteres")
    @Schema(description = "Código do banco (ex: 001, 237, 341)", example = "001")
    private String bancoCodigo;

    @NotBlank(message = "Nome do banco é obrigatório")
    @Size(max = 255, message = "Nome do banco deve ter no máximo 255 caracteres")
    @Schema(description = "Nome do banco", example = "Banco do Brasil")
    private String bancoNome;

    @NotBlank(message = "Agência é obrigatória")
    @Size(max = 20, message = "Agência deve ter no máximo 20 caracteres")
    @Schema(description = "Número da agência", example = "1234")
    private String agencia;

    @Size(max = 2, message = "Dígito da agência deve ter no máximo 2 caracteres")
    @Schema(description = "Dígito verificador da agência", example = "5")
    private String agenciaDigito;

    @NotBlank(message = "Número da conta é obrigatório")
    @Size(max = 50, message = "Número da conta deve ter no máximo 50 caracteres")
    @Schema(description = "Número da conta", example = "12345678")
    private String numeroConta;

    @Size(max = 2, message = "Dígito da conta deve ter no máximo 2 caracteres")
    @Schema(description = "Dígito verificador da conta", example = "9")
    private String contaDigito;

    @NotBlank(message = "Tipo de conta é obrigatório")
    @Schema(description = "Tipo de conta: CORRENTE, POUPANCA, PAGAMENTO, SALARIO", example = "CORRENTE")
    private String tipoConta;

    @Schema(description = "Indica se é a conta principal do estabelecimento", example = "true")
    private Boolean contaPrincipal;

    @Schema(description = "Tipo de chave PIX: CPF, CNPJ, EMAIL, TELEFONE, ALEATORIA", example = "CNPJ")
    private String tipoChavePix;

    @Size(max = 255, message = "Chave PIX deve ter no máximo 255 caracteres")
    @Schema(description = "Valor da chave PIX", example = "12345678000190")
    private String chavePix;

    @NotBlank(message = "Nome do titular é obrigatório")
    @Size(max = 255, message = "Nome do titular deve ter no máximo 255 caracteres")
    @Schema(description = "Nome do titular da conta", example = "Clínica Exemplo LTDA")
    private String titularNome;

    @NotBlank(message = "CPF/CNPJ do titular é obrigatório")
    @Size(max = 18, message = "CPF/CNPJ do titular deve ter no máximo 18 caracteres")
    @Schema(description = "CPF ou CNPJ do titular", example = "12.345.678/0001-90")
    private String titularCpfCnpj;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    @Schema(description = "Observações sobre a conta bancária")
    private String observacoes;
}
