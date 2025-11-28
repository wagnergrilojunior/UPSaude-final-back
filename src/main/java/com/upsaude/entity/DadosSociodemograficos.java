package com.upsaude.entity;

import com.upsaude.enums.CondicaoMoradiaEnum;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SituacaoFamiliarEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade para armazenar dados sociodemográficos do paciente.
 * Relacionamento 1:1 com Paciente.
 * Utilizada para integração com SUS/e-SUS/SISAB/RNDS.
 */
@Entity
@Table(name = "dados_sociodemograficos", schema = "public",
       indexes = {
           @Index(name = "idx_dados_sociodemograficos_paciente", columnList = "paciente_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DadosSociodemograficos extends BaseEntity {

    /**
     * Relacionamento 1:1 com Paciente.
     * O paciente possui um único registro de dados sociodemográficos.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    /**
     * Raça/Cor conforme classificação do IBGE.
     * Utilizado para políticas de equidade em saúde.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "raca_cor")
    private RacaCorEnum racaCor;

    /**
     * Nacionalidade do paciente.
     * Importante para atendimento a estrangeiros e registro no SUS.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    /**
     * País de nascimento.
     * Campo texto livre para países não brasileiros.
     */
    @Size(max = 100, message = "País de nascimento deve ter no máximo 100 caracteres")
    @Column(name = "pais_nascimento", length = 100)
    private String paisNascimento;

    /**
     * Naturalidade do paciente (cidade onde nasceu).
     */
    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    @Column(name = "naturalidade", length = 100)
    private String naturalidade;

    /**
     * Código IBGE do município de nascimento.
     * Utilizado para integração com sistemas governamentais.
     */
    @Size(max = 7, message = "Código IBGE do município deve ter no máximo 7 caracteres")
    @Column(name = "municipio_nascimento_ibge", length = 7)
    private String municipioNascimentoIbge;

    /**
     * Escolaridade do paciente.
     * Importante para indicadores sociais e de saúde.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    /**
     * Ocupação/Profissão do paciente.
     * Campo texto livre para descrição da profissão.
     */
    @Size(max = 150, message = "Ocupação/Profissão deve ter no máximo 150 caracteres")
    @Column(name = "ocupacao_profissao", length = 150)
    private String ocupacaoProfissao;

    /**
     * Indica se o paciente está em situação de rua.
     * Importante para políticas públicas de atenção básica.
     */
    @Column(name = "situacao_rua", nullable = false)
    private Boolean situacaoRua = false;

    /**
     * Tempo em situação de rua (em meses).
     * Preenchido apenas se situacaoRua = true.
     */
    @Column(name = "tempo_situacao_rua")
    private Integer tempoSituacaoRua;

    /**
     * Condição de moradia do paciente.
     * Utilizado para indicadores de vulnerabilidade social.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "condicao_moradia")
    private CondicaoMoradiaEnum condicaoMoradia;

    /**
     * Situação familiar do paciente.
     * Importante para avaliação do contexto social e apoio familiar.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "situacao_familiar")
    private SituacaoFamiliarEnum situacaoFamiliar;
}

