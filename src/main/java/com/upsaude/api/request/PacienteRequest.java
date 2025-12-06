package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;
    
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;
    
    @Pattern(regexp = "^\\d{15}$", message = "CNS deve ter 15 dígitos")
    private String cns;
    private LocalDate dataNascimento;
    private SexoEnum sexo;
    private EstadoCivilEnum estadoCivil;
    private String telefone;
    private String email;
    private String nomeMae;
    private String nomePai;
    private String responsavelNome;
    @Pattern(regexp = "^\\d{11}$", message = "CPF do responsável deve ter 11 dígitos")
    private String responsavelCpf;
    
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone do responsável deve ter 10 ou 11 dígitos")
    private String responsavelTelefone;
    private UUID convenio;
    private String numeroCarteirinha;
    private LocalDate dataValidadeCarteirinha;
    private String observacoes;
    private RacaCorEnum racaCor;
    private NacionalidadeEnum nacionalidade;
    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;
    private EscolaridadeEnum escolaridade;
    private String ocupacaoProfissao;
    private Boolean situacaoRua;
    private StatusPacienteEnum statusPaciente;
    private LocalDate dataObito;
    private String causaObitoCid10;
    private Boolean cartaoSusAtivo;
    private LocalDate dataAtualizacaoCns;
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;
    private String origemCadastro;
    private String nomeSocial;
    private IdentidadeGeneroEnum identidadeGenero;
    private OrientacaoSexualEnum orientacaoSexual;
    private Boolean possuiDeficiencia;
    private String tipoDeficiencia;
    private Boolean cnsValidado;
    private TipoCnsEnum tipoCns;
    private Boolean acompanhadoPorEquipeEsf;
    @Builder.Default
    private List<EnderecoRequest> enderecos = new ArrayList<>();
    
    private UUID dadosSociodemograficos;
    private UUID dadosClinicosBasicos;
    private UUID responsavelLegal;
    private UUID lgpdConsentimento;
    private UUID integracaoGov;
    
    @Builder.Default
    private List<DoencasPacienteRequest> doencas = new ArrayList<>();
    
    @Builder.Default
    private List<AlergiasPacienteRequest> alergias = new ArrayList<>();
    
    @Builder.Default
    private List<DeficienciasPacienteRequest> deficiencias = new ArrayList<>();
    
    @Builder.Default
    private List<MedicacaoPacienteRequest> medicacoes = new ArrayList<>();
}
