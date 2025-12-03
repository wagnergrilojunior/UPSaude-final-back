package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FabricantesMedicamentoRequest {
    private String nome;
    private String pais;
    private String contatoJson;
}
