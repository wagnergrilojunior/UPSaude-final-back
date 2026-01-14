package com.upsaude.api.request.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.api.request.embeddable.ContatoEstabelecimentoRequest;
import com.upsaude.api.request.embeddable.DadosIdentificacaoEstabelecimentoRequest;
import com.upsaude.api.request.embeddable.InfraestruturaFisicaEstabelecimentoRequest;
import com.upsaude.api.request.embeddable.LicenciamentoEstabelecimentoRequest;
import com.upsaude.api.request.embeddable.LocalizacaoEstabelecimentoRequest;
import com.upsaude.api.request.embeddable.ResponsaveisEstabelecimentoRequest;
import com.upsaude.api.request.geral.EnderecoRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@Schema(description = "Dados de estabelecimentos")
public class EstabelecimentosRequest {

    @NotNull(message = "Dados de identificação são obrigatórios")
    @Valid
    private DadosIdentificacaoEstabelecimentoRequest dadosIdentificacao;

    @Size(max = 50, message = "Registro oficial deve ter no máximo 50 caracteres")
    private String registroOficial;

    private UUID enderecoPrincipal;

    @Deprecated
    @Valid
    private EnderecoRequest enderecoPrincipalCompleto;

    @JsonSetter("enderecoPrincipal")
    public void setEnderecoPrincipalFlexivel(JsonNode node) {
        if (node == null || node.isNull()) {
            this.enderecoPrincipal = null;
            this.enderecoPrincipalCompleto = null;
            return;
        }

        try {
            if (node.isTextual()) {
                String uuidString = node.asText();
                if (uuidString != null && !uuidString.trim().isEmpty()) {
                    this.enderecoPrincipal = UUID.fromString(uuidString);
                    this.enderecoPrincipalCompleto = null;
                }
            } else if (node.isObject()) {
                ObjectMapper mapper = new ObjectMapper();
                this.enderecoPrincipalCompleto = mapper.treeToValue(node, EnderecoRequest.class);
                this.enderecoPrincipal = null;
                log.debug("Endereço principal recebido como objeto, convertido para enderecoPrincipalCompleto");
            } else {
                log.warn("Tipo inesperado para enderecoPrincipal: {}", node.getNodeType());
                this.enderecoPrincipal = null;
                this.enderecoPrincipalCompleto = null;
            }
        } catch (Exception e) {
            log.error("Erro ao processar enderecoPrincipal: {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Erro ao processar enderecoPrincipal. Deve ser UUID (string) ou objeto EnderecoRequest.", e);
        }
    }

    @Valid
    private ContatoEstabelecimentoRequest contato;

    @Valid
    private ResponsaveisEstabelecimentoRequest responsaveis;

    @Valid
    private LicenciamentoEstabelecimentoRequest licenciamento;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataLicenciamento;

    private OffsetDateTime dataValidadeLicencaSanitaria;

    @Valid
    private InfraestruturaFisicaEstabelecimentoRequest infraestruturaFisica;

    @Valid
    private LocalizacaoEstabelecimentoRequest localizacao;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;

    @NotNull(message = "Prestador de serviço é obrigatório")
    @Schema(description = "Indica se o estabelecimento é um prestador de serviço")
    private Boolean prestadorServico;

    @Schema(description = "Lista de contas bancárias do estabelecimento")
    @Valid
    private java.util.List<ContaBancariaEstabelecimentoRequest> contasBancarias;
}
