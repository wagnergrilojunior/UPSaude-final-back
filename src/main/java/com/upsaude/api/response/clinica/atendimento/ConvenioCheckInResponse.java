package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.enums.TipoConvenioEnum;
import java.util.UUID;
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
public class ConvenioCheckInResponse {
    private UUID id;
    private String nome;
    private TipoConvenioEnum tipo;
}
