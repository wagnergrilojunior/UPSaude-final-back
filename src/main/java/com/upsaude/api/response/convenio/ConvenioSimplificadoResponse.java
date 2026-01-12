package com.upsaude.api.response.convenio;

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
public class ConvenioSimplificadoResponse {
    private UUID id;
    private String nome;
    private UUID estabelecimentoId;
    private UUID tenantId;
}
