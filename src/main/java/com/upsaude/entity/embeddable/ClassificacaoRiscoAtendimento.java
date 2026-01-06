package com.upsaude.entity.embeddable;

import com.upsaude.enums.ClassificacaoRiscoEnum;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.util.converter.ClassificacaoRiscoEnumConverter;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ClassificacaoRiscoAtendimento {

    public ClassificacaoRiscoAtendimento() {
        this.gravidade = "";
        this.necessitaObservacao = false;
        this.necessitaInternacao = false;
    }

    @Convert(converter = ClassificacaoRiscoEnumConverter.class)
    @Column(name = "classificacao_risco")
    private ClassificacaoRiscoEnum classificacaoRisco;

    @Convert(converter = PrioridadeAtendimentoEnumConverter.class)
    @Column(name = "prioridade")
    private PrioridadeAtendimentoEnum prioridade;

    @Column(name = "gravidade", length = 50)
    private String gravidade;

    @Column(name = "necessita_observacao", nullable = false)
    @Builder.Default
    private Boolean necessitaObservacao = false;

    @Column(name = "necessita_internacao", nullable = false)
    @Builder.Default
    private Boolean necessitaInternacao = false;
}
