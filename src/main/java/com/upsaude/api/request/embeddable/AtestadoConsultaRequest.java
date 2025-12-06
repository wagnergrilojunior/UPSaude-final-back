package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtestadoConsultaRequest {
    @NotNull(message = "Atestado emitido é obrigatório")
    @Builder.Default
    private Boolean atestadoEmitido = false;
    
    @Size(max = 100, message = "Tipo de atestado deve ter no máximo 100 caracteres")
    private String tipoAtestado;
    
    private Integer diasAfastamento;
    
    private LocalDate dataInicioAfastamento;
    
    private LocalDate dataFimAfastamento;
    
    private String motivoAtestado;
    
    @Size(max = 10, message = "CID do atestado deve ter no máximo 10 caracteres")
    private String cidAtestado;
}
