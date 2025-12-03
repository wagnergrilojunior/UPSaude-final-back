package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadesRequest {
    private String nome;
    private String codigoIbge;
    private Double latitude;
    private Double longitude;
    private UUID estado;
}
