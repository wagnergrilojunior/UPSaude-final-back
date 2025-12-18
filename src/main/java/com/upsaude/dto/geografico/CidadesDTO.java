package com.upsaude.dto.geografico;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadesDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String codigoIbge;
    private Double latitude;
    private Double longitude;
    private EstadosDTO estado;
}
