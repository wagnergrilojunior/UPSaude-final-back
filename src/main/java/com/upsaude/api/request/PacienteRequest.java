package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.SexoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacienteRequest {
    // ========== CAMPOS OBRIGATÓRIOS ==========
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome completo")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    // ========== CAMPOS OPCIONAIS COM VALIDAÇÕES ==========
    
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter 11 dígitos")
    private String cpf;
    
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;
    
    @Pattern(regexp = "^[0-9]{15}$", message = "CNS deve conter 15 dígitos")
    private String cns;
    
    private LocalDate dataNascimento;
    
    @NotNull(message = "Sexo é obrigatório")
    private SexoEnum sexo;
    
    private EstadoCivilEnum estadoCivil;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone deve conter DDD + número (10 ou 11 dígitos)")
    private String telefone;
    
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome da mãe")
    @Size(max = 255, message = "Nome da mãe deve ter no máximo 255 caracteres")
    private String nomeMae;
    
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome do pai")
    @Size(max = 255, message = "Nome do pai deve ter no máximo 255 caracteres")
    private String nomePai;
    
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome do responsável")
    @Size(max = 255, message = "Nome do responsável deve ter no máximo 255 caracteres")
    private String responsavelNome;
    
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF do responsável deve conter 11 dígitos")
    private String responsavelCpf;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone do responsável deve conter DDD + número (10 ou 11 dígitos)")
    private String responsavelTelefone;
    
    @Size(max = 50, message = "Número da carteirinha deve ter no máximo 50 caracteres")
    private String numeroCarteirinha;
    
    private LocalDate dataValidadeCarteirinha;
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
    
    // ========== CAMPOS JSON (DEPRECADOS - MANTER PARA COMPATIBILIDADE) ==========
    
    private String enderecoJson;
    private String contatoJson;
    private String informacoesAdicionaisJson;
    
    // ========== IDs DE RELACIONAMENTO ==========
    
    private UUID convenioId;
    private UUID convenio; // Duplicado - manter para compatibilidade
    private UUID estabelecimentoId;
    private UUID dadosSociodemograficos;
    private UUID dadosClinicosBasicos;
    private UUID responsavelLegal;
    private UUID lgpdConsentimento;
    private UUID integracaoGov;
    
    // ========== COLETES DE RELACIONAMENTO (SET) ==========
    
    private Set<UUID> enderecosIds;
    private Set<UUID> deficiencias;
    private Set<UUID> medicacoes;
    private Set<UUID> alergias;
    private Set<UUID> doencas;
}

