package com.upsaude.api.request.referencia.fabricante;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de fabricantes medicamento")
public class FabricantesMedicamentoRequest {
    private String nome;
    private String pais;
    private String contatoJson;
}
