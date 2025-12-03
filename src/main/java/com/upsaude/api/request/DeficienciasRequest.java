package com.upsaude.api.request;

import com.upsaude.enums.TipoDeficienciaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasRequest {
    private String nome;
    private String descricao;
    private TipoDeficienciaEnum tipoDeficiencia;
    private String cid10Relacionado;
}
