package com.upsaude.api.response;

import com.upsaude.enums.TipoDeficienciaEnum;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para Deficiências.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasResponse {
    private UUID id;
    private String nome;
    private String descricao;
    private TipoDeficienciaEnum tipoDeficiencia;
    private String cid10Relacionado;
    private Boolean permanente;
    private Boolean acompanhamentoContinuo;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

