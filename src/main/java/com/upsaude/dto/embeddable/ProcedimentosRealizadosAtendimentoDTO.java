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
public class ProcedimentosRealizadosAtendimentoDTO {
    private String procedimentosRealizados;
    private String examesSolicitados;
    private String medicamentosPrescritos;
    private String orientacoes;
    private String encaminhamentos;
}
