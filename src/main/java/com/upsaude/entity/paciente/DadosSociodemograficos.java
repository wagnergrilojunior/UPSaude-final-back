package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;

import com.upsaude.enums.CondicaoMoradiaEnum;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SituacaoFamiliarEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paciente_dados_sociodemograficos", schema = "public", indexes = {
        @Index(name = "idx_paciente_dados_sociodemograficos_paciente", columnList = "paciente_id")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DadosSociodemograficos extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "raca_cor")
    private RacaCorEnum racaCor;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    @Column(name = "pais_nascimento", length = 100)
    private String paisNascimento;

    @Column(name = "naturalidade", length = 100)
    private String naturalidade;

    @Column(name = "municipio_nascimento_ibge", length = 7)
    private String municipioNascimentoIbge;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    @Column(name = "ocupacao_profissao", length = 150)
    private String ocupacaoProfissao;

    @Column(name = "situacao_rua")
    private Boolean situacaoRua = false;

    @Column(name = "tempo_situacao_rua")
    private Integer tempoSituacaoRua;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "condicao_moradia")
    private CondicaoMoradiaEnum condicaoMoradia;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "situacao_familiar")
    private SituacaoFamiliarEnum situacaoFamiliar;
}
