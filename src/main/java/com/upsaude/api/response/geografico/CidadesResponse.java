package com.upsaude.api.response.geografico;
import com.upsaude.api.response.geografico.EstadosResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadesResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String codigoIbge;
    private Double latitude;
    private Double longitude;
    private EstadosResponse estado;
}
