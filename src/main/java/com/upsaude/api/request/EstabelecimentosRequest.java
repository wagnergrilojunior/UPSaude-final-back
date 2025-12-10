package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstabelecimentosRequest {
    // ========== CAMPOS OBRIGATÓRIOS ==========
    
    @NotBlank(message = "Nome do estabelecimento é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome fantasia")
    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;
    
    @NotNull(message = "Tipo de estabelecimento é obrigatório")
    private TipoEstabelecimentoEnum tipo;
    
    // ========== CAMPOS OPCIONAIS COM VALIDAÇÕES GOVERNAMENTAIS ==========
    
    @Pattern(regexp = "^[0-9]{7}$", message = "CNES deve conter 7 dígitos")
    @Size(max = 7, message = "Código CNES deve ter no máximo 7 caracteres")
    private String codigoCnes;
    
    @Pattern(regexp = "^[0-9]{14}$", message = "CNPJ deve conter 14 dígitos")
    private String cnpj;
    
    private NaturezaJuridicaEnum naturezaJuridica;
    
    @Size(max = 50, message = "Registro oficial deve ter no máximo 50 caracteres")
    private String registroOficial;
    
    // ========== IDs DE RELACIONAMENTO ==========
    
    /**
     * Endereço principal do estabelecimento.
     * IMPORTANTE: Apenas UUID deve ser usado. Para criar novos endereços, use o endpoint de endereços separadamente.
     * @deprecated enderecoPrincipalCompleto será removido em versão futura - use apenas enderecoPrincipal (UUID)
     */
    private UUID enderecoPrincipal;
    
    /**
     * @deprecated Use apenas enderecoPrincipal (UUID). Para criar novos endereços, use o endpoint de endereços separadamente.
     */
    @Deprecated
    @Valid
    private EnderecoRequest enderecoPrincipalCompleto;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone deve conter DDD + número (10 ou 11 dígitos)")
    private String telefone;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone secundário deve conter DDD + número (10 ou 11 dígitos)")
    private String telefoneSecundario;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Fax deve conter DDD + número (10 ou 11 dígitos)")
    private String fax;
    
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @Pattern(regexp = "^(https?://).+", message = "URL inválida, deve iniciar com http:// ou https://")
    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    private String site;
    private UUID responsavelTecnico;
    private UUID responsavelAdministrativo;
    @Size(max = 255, message = "Nome do responsável legal deve ter no máximo 255 caracteres")
    private String responsavelLegalNome;
    
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF do responsável legal deve conter 11 dígitos")
    private String responsavelLegalCpf;
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
