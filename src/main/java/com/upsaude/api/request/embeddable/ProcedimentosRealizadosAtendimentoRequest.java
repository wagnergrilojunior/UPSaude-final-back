package com.upsaude.api.request.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de procedimentos realizados atendimento")
public class ProcedimentosRealizadosAtendimentoRequest {
    private String procedimentosRealizados;
    private String examesSolicitados;
    private String medicamentosPrescritos;
    private String orientacoes;
    private String encaminhamentos;
}
