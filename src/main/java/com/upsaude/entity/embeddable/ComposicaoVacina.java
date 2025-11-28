package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de composição e tecnologia da vacina.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComposicaoVacina {

    @Column(name = "composicao", columnDefinition = "TEXT")
    private String composicao; // Composição da vacina

    @Column(name = "tecnologia", length = 100)
    private String tecnologia; // Ex: Vírus inativado, Vírus atenuado, Subunidade, mRNA

    @Column(name = "adjuvante", length = 100)
    private String adjuvante; // Adjuvante utilizado (se houver)

    @Column(name = "conservante", length = 100)
    private String conservante; // Conservante utilizado (se houver)
}

