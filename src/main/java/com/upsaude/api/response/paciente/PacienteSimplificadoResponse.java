package com.upsaude.api.response.paciente;

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
import java.time.LocalDate;
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
public class PacienteSimplificadoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nomeCompleto;
    private String cpf;
    private String rg;
    private String cns;
    private LocalDate dataNascimento;
    private SexoEnum sexo;
    private EstadoCivilEnum estadoCivil;
    private String telefone;
    private String email;
    private String nomeMae;
    private String nomePai;
    private String responsavelNome;
    private String responsavelCpf;
    private String responsavelTelefone;
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
}
