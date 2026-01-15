package com.upsaude.api.response.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta com dados de conta bancária do estabelecimento")
public class ContaBancariaEstabelecimentoResponse {

    @Schema(description = "ID da conta bancária")
    private UUID id;

    @Schema(description = "Código do banco", example = "001")
    private String bancoCodigo;

    @Schema(description = "Nome do banco", example = "Banco do Brasil")
    private String bancoNome;

    @Schema(description = "Número da agência", example = "1234")
    private String agencia;

    @Schema(description = "Dígito verificador da agência", example = "5")
    private String agenciaDigito;

    @Schema(description = "Número da conta", example = "12345678")
    private String numeroConta;

    @Schema(description = "Dígito verificador da conta", example = "9")
    private String contaDigito;

    @Schema(description = "Tipo de conta", example = "CORRENTE")
    private String tipoConta;

    @Schema(description = "Indica se é a conta principal", example = "true")
    private Boolean contaPrincipal;

    @Schema(description = "Tipo de chave PIX", example = "CNPJ")
    private String tipoChavePix;

    @Schema(description = "Valor da chave PIX", example = "12345678000190")
    private String chavePix;

    @Schema(description = "Nome do titular da conta", example = "Clínica Exemplo LTDA")
    private String titularNome;

    @Schema(description = "CPF ou CNPJ do titular", example = "12.345.678/0001-90")
    private String titularCpfCnpj;

    @Schema(description = "Observações sobre a conta bancária")
    private String observacoes;

    @Schema(description = "Indica se a conta está ativa")
    private Boolean ativo;

    @Schema(description = "Data de criação")
    private OffsetDateTime criadoEm;

    @Schema(description = "Data de atualização")
    private OffsetDateTime atualizadoEm;
}
