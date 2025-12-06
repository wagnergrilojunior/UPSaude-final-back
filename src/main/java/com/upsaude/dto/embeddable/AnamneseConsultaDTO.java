package com.upsaude.dto.embeddable;

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
public class AnamneseConsultaDTO {
    private String queixaPrincipal;
    private String historiaDoencaAtual;
    private String antecedentesPessoais;
    private String antecedentesFamiliares;
    private String medicamentosUso;
    private String alergias;
    private String habitosVida;
    private String exameFisico;
    private String sinaisVitais;
}
