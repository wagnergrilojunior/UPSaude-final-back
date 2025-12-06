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
public class PrescricaoConsultaDTO {
    private String medicamentosPrescritos;
    private String orientacoes;
    private String dieta;
    private String atividadeFisica;
    private String repouso;
    private String outrasOrientacoes;
}
