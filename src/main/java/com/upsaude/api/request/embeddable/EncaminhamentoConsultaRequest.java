package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EncaminhamentoConsultaRequest {
    private String encaminhamentos;

    @Size(max = 255, message = "Especialista encaminhado deve ter no máximo 255 caracteres")
    private String especialistaEncaminhado;

    private String motivoEncaminhamento;

    @NotNull(message = "Urgência do encaminhamento é obrigatório")
    @Builder.Default
    private Boolean urgenciaEncaminhamento = false;

    @Size(max = 50, message = "Prazo do encaminhamento deve ter no máximo 50 caracteres")
    private String prazoEncaminhamento;
}
