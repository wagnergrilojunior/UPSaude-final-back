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
public class EncaminhamentoConsultaDTO {
    private String encaminhamentos;
    private String especialistaEncaminhado;
    private String motivoEncaminhamento;
    private Boolean urgenciaEncaminhamento;
    private String prazoEncaminhamento;
}
