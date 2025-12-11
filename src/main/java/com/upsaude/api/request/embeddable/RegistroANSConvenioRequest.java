package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
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
public class RegistroANSConvenioRequest {
    @Size(max = 50, message = "Registro ANS deve ter no máximo 50 caracteres")
    private String registroAns;
    
    @Size(max = 50, message = "Código ANS deve ter no máximo 50 caracteres")
    private String codigoAns;
    
    private LocalDate dataRegistroAns;
    
    private LocalDate dataValidadeRegistroAns;
    
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum statusAns;
    
    @Size(max = 100, message = "Razão social ANS deve ter no máximo 100 caracteres")
    private String razaoSocialAns;
    
    @Size(max = 50, message = "Nome fantasia ANS deve ter no máximo 50 caracteres")
    private String nomeFantasiaAns;
    
    @Size(max = 50, message = "Código TISS deve ter no máximo 50 caracteres")
    private String codigoTiss;
    
    @NotNull(message = "Habilitado TISS é obrigatório")
    @Builder.Default
    private Boolean habilitadoTiss = false;
    
    @Size(max = 255, message = "Observações ANS deve ter no máximo 255 caracteres")
    private String observacoesAns;
}
