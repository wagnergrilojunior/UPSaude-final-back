package com.upsaude.dto.embeddable;

import com.upsaude.enums.StatusAtivoEnum;
import java.time.LocalDate;
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
public class RegistroANSConvenioDTO {
    private String registroAns;
    private String codigoAns;
    private LocalDate dataRegistroAns;
    private LocalDate dataValidadeRegistroAns;
    private StatusAtivoEnum statusAns;
    private String razaoSocialAns;
    private String nomeFantasiaAns;
    private String codigoTiss;
    private Boolean habilitadoTiss;
    private String observacoesAns;
}
