package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import com.upsaude.util.converter.NaturezaJuridicaEnumDeserializer;
import com.upsaude.util.converter.StatusFuncionamentoEnumDeserializer;
import com.upsaude.util.converter.TipoEstabelecimentoEnumDeserializer;
import com.upsaude.validation.annotation.CNPJValido;
import com.upsaude.validation.annotation.CNESValido;
import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.SiteValido;
import com.upsaude.validation.annotation.TelefoneValido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @NotBlank(message = "Nome do estabelecimento é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome fantasia")
    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;

    @NotNull(message = "Tipo de estabelecimento é obrigatório")
    @JsonDeserialize(using = TipoEstabelecimentoEnumDeserializer.class)
    private TipoEstabelecimentoEnum tipo;

    @CNESValido
    @Size(max = 7, message = "Código CNES deve ter no máximo 7 caracteres")
    private String codigoCnes;

    @CNPJValido
    private String cnpj;

    @JsonDeserialize(using = NaturezaJuridicaEnumDeserializer.class)
    private NaturezaJuridicaEnum naturezaJuridica;

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
            }

            else if (node.isObject()) {
                ObjectMapper mapper = new ObjectMapper();
                this.enderecoPrincipalCompleto = mapper.treeToValue(node, EnderecoRequest.class);
                this.enderecoPrincipal = null;
                log.debug("Endereço principal recebido como objeto, convertido para enderecoPrincipalCompleto");
            }

            else {
                log.warn("Tipo inesperado para enderecoPrincipal: {}", node.getNodeType());
                this.enderecoPrincipal = null;
                this.enderecoPrincipalCompleto = null;
            }
        } catch (Exception e) {
            log.error("Erro ao processar enderecoPrincipal: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Erro ao processar enderecoPrincipal. Deve ser UUID (string) ou objeto EnderecoRequest.", e);
        }
    }

    @TelefoneValido
    private String telefone;

    @TelefoneValido
    private String telefoneSecundario;

    @TelefoneValido
    private String fax;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @SiteValido
    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    private String site;
    private UUID responsavelTecnico;
    private UUID responsavelAdministrativo;
    @Size(max = 255, message = "Nome do responsável legal deve ter no máximo 255 caracteres")
    private String responsavelLegalNome;

    @CPFValido
    private String responsavelLegalCpf;
    @JsonDeserialize(using = StatusFuncionamentoEnumDeserializer.class)
    private StatusFuncionamentoEnum statusFuncionamento;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataLicenciamento;
    private OffsetDateTime dataValidadeLicenca;
    private String numeroAlvara;
    private String numeroLicencaSanitaria;
    private OffsetDateTime dataValidadeLicencaSanitaria;
    @Min(value = 0, message = "Quantidade de leitos não pode ser negativa")
    @Max(value = 10000, message = "Quantidade de leitos não pode ser maior que 10000")
    private Integer quantidadeLeitos;

    @Min(value = 0, message = "Quantidade de consultórios não pode ser negativa")
    @Max(value = 1000, message = "Quantidade de consultórios não pode ser maior que 1000")
    private Integer quantidadeConsultorios;

    @Min(value = 0, message = "Quantidade de salas de cirurgia não pode ser negativa")
    @Max(value = 500, message = "Quantidade de salas de cirurgia não pode ser maior que 500")
    private Integer quantidadeSalasCirurgia;

    @Min(value = 0, message = "Quantidade de ambulatórios não pode ser negativa")
    @Max(value = 500, message = "Quantidade de ambulatórios não pode ser maior que 500")
    private Integer quantidadeAmbulatorios;
    private Double areaConstruidaMetrosQuadrados;
    private Double areaTotalMetrosQuadrados;
    private Double latitude;
    private Double longitude;
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
