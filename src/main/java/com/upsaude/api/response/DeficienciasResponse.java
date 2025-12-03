package com.upsaude.api.response;

import com.upsaude.enums.TipoDeficienciaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String descricao;
    private TipoDeficienciaEnum tipoDeficiencia;
    private String cid10Relacionado;
}
