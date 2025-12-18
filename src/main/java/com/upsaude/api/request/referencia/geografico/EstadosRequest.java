package com.upsaude.api.request.referencia.geografico;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de estados")
public class EstadosRequest {
    private String sigla;
    private String nome;
    private String codigoIbge;
}
