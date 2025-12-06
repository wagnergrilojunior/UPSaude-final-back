package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para classificação de risco do atendimento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ClassificacaoRiscoAtendimento {

    public ClassificacaoRiscoAtendimento() {
        this.classificacaoRisco = "";
        this.prioridade = "";
        this.gravidade = "";
        this.necessitaObservacao = false;
        this.necessitaInternacao = false;
    }

    @Size(max = 50, message = "Classificação de risco deve ter no máximo 50 caracteres")
    @Column(name = "classificacao_risco", length = 50)
    private String classificacaoRisco; // Ex: Vermelho, Laranja, Amarelo, Verde, Azul (protocolo Manchester)

    @Size(max = 50, message = "Prioridade deve ter no máximo 50 caracteres")
    @Column(name = "prioridade", length = 50)
    private String prioridade; // Ex: Emergência, Urgência, Pouco Urgente, Não Urgente

    @Column(name = "gravidade", length = 50)
    private String gravidade; // Ex: Leve, Moderada, Grave, Muito Grave, Crítica

    @Column(name = "necessita_observacao", nullable = false)
    @Builder.Default
    private Boolean necessitaObservacao = false; // Se necessita observação após o atendimento

    @Column(name = "necessita_internacao", nullable = false)
    @Builder.Default
    private Boolean necessitaInternacao = false; // Se necessita internação
}

