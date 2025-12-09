package com.upsaude.repository.projection;

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

/**
 * Projeção para buscar apenas os campos necessários do Paciente
 * sem carregar relacionamentos lazy, melhorando significativamente a performance.
 */
public interface PacienteSimplificadoProjection {
    UUID getId();
    OffsetDateTime getCreatedAt();
    OffsetDateTime getUpdatedAt();
    Boolean getActive();
    String getNomeCompleto();
    String getCpf();
    String getRg();
    String getCns();
    LocalDate getDataNascimento();
    SexoEnum getSexo();
    EstadoCivilEnum getEstadoCivil();
    String getTelefone();
    String getEmail();
    String getNomeMae();
    String getNomePai();
    String getResponsavelNome();
    String getResponsavelCpf();
    String getResponsavelTelefone();
    String getNumeroCarteirinha();
    LocalDate getDataValidadeCarteirinha();
    String getObservacoes();
    RacaCorEnum getRacaCor();
    NacionalidadeEnum getNacionalidade();
    String getPaisNascimento();
    String getNaturalidade();
    String getMunicipioNascimentoIbge();
    EscolaridadeEnum getEscolaridade();
    String getOcupacaoProfissao();
    Boolean getSituacaoRua();
    StatusPacienteEnum getStatusPaciente();
    LocalDate getDataObito();
    String getCausaObitoCid10();
    Boolean getCartaoSusAtivo();
    LocalDate getDataAtualizacaoCns();
    TipoAtendimentoPreferencialEnum getTipoAtendimentoPreferencial();
    String getOrigemCadastro();
    String getNomeSocial();
    IdentidadeGeneroEnum getIdentidadeGenero();
    OrientacaoSexualEnum getOrientacaoSexual();
    Boolean getPossuiDeficiencia();
    String getTipoDeficiencia();
    Boolean getCnsValidado();
    TipoCnsEnum getTipoCns();
    Boolean getAcompanhadoPorEquipeEsf();
}
