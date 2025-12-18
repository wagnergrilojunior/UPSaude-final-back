package com.upsaude.dto.paciente;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.enums.TipoCnsEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
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
    private ConvenioDTO convenio;
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
    private LocalDate dataObito;
    private String causaObitoCid10;
    private LocalDate dataAtualizacaoCns;
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;
    private String origemCadastro;
    private String nomeSocial;
    private IdentidadeGeneroEnum identidadeGenero;
    private OrientacaoSexualEnum orientacaoSexual;
    private String tipoDeficiencia;
    private TipoCnsEnum tipoCns;
    private DadosSociodemograficosDTO dadosSociodemograficos;
    private DadosClinicosBasicosDTO dadosClinicosBasicos;
    private ResponsavelLegalDTO responsavelLegal;
    private LGPDConsentimentoDTO lgpdConsentimento;
    private IntegracaoGovDTO integracaoGov;
}
