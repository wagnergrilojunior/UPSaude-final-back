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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "raca_cor")
    private RacaCorEnum racaCor;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    @Size(max = 100, message = "País de nascimento deve ter no máximo 100 caracteres")
    @Column(name = "pais_nascimento", length = 100)
    private String paisNascimento;

    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    @Column(name = "naturalidade", length = 100)
    private String naturalidade;

    @Size(max = 7, message = "Código IBGE do município deve ter no máximo 7 caracteres")
    @Column(name = "municipio_nascimento_ibge", length = 7)
    private String municipioNascimentoIbge;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    @Size(max = 150, message = "Ocupação/Profissão deve ter no máximo 150 caracteres")
    @Column(name = "ocupacao_profissao", length = 150)
    private String ocupacaoProfissao;

    @Column(name = "situacao_rua", nullable = false)
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
