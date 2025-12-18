package com.upsaude.api.request.paciente;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.EstadoCivilEnumDeserializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.OrientacaoSexualEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.SexoEnumDeserializer;
import com.upsaude.util.converter.StatusPacienteEnumDeserializer;
import com.upsaude.validation.annotation.CNSValido;
import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.TelefoneValido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de paciente")
public class PacienteRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome completo")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;

    @CPFValido
    @NotNull(message = "CPF do paciente é obrigatório")
    private String cpf;

    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @CNSValido
    private String cns;

    private LocalDate dataNascimento;

    @NotNull(message = "Sexo é obrigatório")
    @JsonDeserialize(using = SexoEnumDeserializer.class)
    private SexoEnum sexo;

    @JsonDeserialize(using = EstadoCivilEnumDeserializer.class)
    private EstadoCivilEnum estadoCivil;

    @JsonDeserialize(using = IdentidadeGeneroEnumDeserializer.class)
    private IdentidadeGeneroEnum identidadeGenero;

    @JsonDeserialize(using = OrientacaoSexualEnumDeserializer.class)
    private OrientacaoSexualEnum orientacaoSexual;

    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;

    @JsonDeserialize(using = NacionalidadeEnumDeserializer.class)
    private NacionalidadeEnum nacionalidade;

    private String paisNascimento;

    private String naturalidade;

    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;

    private String ocupacaoProfissao;

    @JsonDeserialize(using = StatusPacienteEnumDeserializer.class)
    private StatusPacienteEnum statusPaciente;

    private String nomeSocial;

    private Boolean situacaoRua;

    private Boolean possuiDeficiencia;

    private Boolean acompanhadoPorEquipeEsf;

    private Boolean cartaoSusAtivo;

    private Boolean cnsValidado;

    @TelefoneValido
    private String telefone;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome da mãe")
    @Size(max = 255, message = "Nome da mãe deve ter no máximo 255 caracteres")
    private String nomeMae;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome do pai")
    @Size(max = 255, message = "Nome do pai deve ter no máximo 255 caracteres")
    private String nomePai;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome do responsável")
    @Size(max = 255, message = "Nome do responsável deve ter no máximo 255 caracteres")
    private String responsavelNome;

    @NotNull(message = "CPF do responsável é obrigatório")
    @CPFValido
    private String responsavelCpf;

    @TelefoneValido
    private String responsavelTelefone;

    @Size(max = 50, message = "Número da carteirinha deve ter no máximo 50 caracteres")
    private String numeroCarteirinha;

    private LocalDate dataValidadeCarteirinha;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;

    private String enderecoJson;
    private String contatoJson;
    private String informacoesAdicionaisJson;

    private UUID convenioId;
    private UUID convenio;
    private UUID estabelecimentoId;
    private UUID dadosSociodemograficos;
    private UUID dadosClinicosBasicos;
    private UUID responsavelLegal;
    private UUID lgpdConsentimento;
    private UUID integracaoGov;

    private Set<UUID> enderecosIds;
    private Set<UUID> deficiencias;
    private Set<UUID> medicacoes;
    private Set<UUID> alergias;
    private Set<UUID> doencas;

    private List<EnderecoRequest> enderecos;
}
