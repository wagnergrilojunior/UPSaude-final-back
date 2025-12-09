package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.EstadoCivilEnumDeserializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.OrientacaoSexualEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.SexoEnumDeserializer;
import com.upsaude.util.converter.StatusPacienteEnumDeserializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumDeserializer;
import com.upsaude.util.converter.TipoCnsEnumDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.enums.TipoCnsEnum;

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
public class PacienteRequest {
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    @Pattern(regexp = "^$|^\\d{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;
    
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;
    
    @Pattern(regexp = "^$|^\\d{15}$", message = "CNS deve ter 15 dígitos")
    private String cns;
    
    private LocalDate dataNascimento;
    
    @JsonDeserialize(using = SexoEnumDeserializer.class)
    private SexoEnum sexo;
    
    @JsonDeserialize(using = EstadoCivilEnumDeserializer.class)
    private EstadoCivilEnum estadoCivil;
    
    @Pattern(regexp = "^$|^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
    
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @Size(max = 255, message = "Nome da mãe deve ter no máximo 255 caracteres")
    private String nomeMae;
    
    @Size(max = 255, message = "Nome do pai deve ter no máximo 255 caracteres")
    private String nomePai;
    
    @Size(max = 255, message = "Nome do responsável deve ter no máximo 255 caracteres")
    private String responsavelNome;
    
    @Pattern(regexp = "^$|^\\d{11}$", message = "CPF do responsável deve ter 11 dígitos")
    private String responsavelCpf;
    
    @Pattern(regexp = "^$|^\\d{10,11}$", message = "Telefone do responsável deve ter 10 ou 11 dígitos")
    private String responsavelTelefone;
    private UUID convenio;
    private String numeroCarteirinha;
    private LocalDate dataValidadeCarteirinha;
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;
    @JsonDeserialize(using = NacionalidadeEnumDeserializer.class)
    private NacionalidadeEnum nacionalidade;
    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;
    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;
    private String ocupacaoProfissao;
    private Boolean situacaoRua;
    @JsonDeserialize(using = StatusPacienteEnumDeserializer.class)
    private StatusPacienteEnum statusPaciente;
    private LocalDate dataObito;
    private String causaObitoCid10;
    private Boolean cartaoSusAtivo;
    private LocalDate dataAtualizacaoCns;
    @JsonDeserialize(using = TipoAtendimentoPreferencialEnumDeserializer.class)
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;
    private String origemCadastro;
    private String nomeSocial;
    @JsonDeserialize(using = IdentidadeGeneroEnumDeserializer.class)
    private IdentidadeGeneroEnum identidadeGenero;
    @JsonDeserialize(using = OrientacaoSexualEnumDeserializer.class)
    private OrientacaoSexualEnum orientacaoSexual;
    private Boolean possuiDeficiencia;
    private String tipoDeficiencia;
    private Boolean cnsValidado;
    @JsonDeserialize(using = TipoCnsEnumDeserializer.class)
    private TipoCnsEnum tipoCns;
    private Boolean acompanhadoPorEquipeEsf;
    @Valid
    @Builder.Default
    private List<EnderecoRequest> enderecos = new ArrayList<>();
    
    private UUID dadosSociodemograficos;
    private UUID dadosClinicosBasicos;
    private UUID responsavelLegal;
    private UUID lgpdConsentimento;
    private UUID integracaoGov;
    
    @Builder.Default
    private Set<UUID> doencas = new HashSet<>();
    
    @Builder.Default
    private Set<UUID> alergias = new HashSet<>();
    
    @Builder.Default
    private Set<UUID> deficiencias = new HashSet<>();
    
    @Builder.Default
    private Set<UUID> medicacoes = new HashSet<>();
}
