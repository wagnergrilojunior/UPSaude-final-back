package com.upsaude.api.request.referencia.geografico;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de cidades")
public class CidadesRequest {
    private String nome;
    private String codigoIbge;
    private Double latitude;
    private Double longitude;
    private UUID estado;
}
