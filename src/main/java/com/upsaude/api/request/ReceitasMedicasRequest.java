package com.upsaude.api.request;

import com.upsaude.enums.StatusReceitaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitasMedicasRequest {
    private UUID medico;
    private UUID paciente;
    private String numeroReceita;
    private OffsetDateTime dataPrescricao;
    private OffsetDateTime dataValidade;
    private Boolean usoContinuo;
    private String observacoes;
    private StatusReceitaEnum status;
    private String origemReceita;
    private UUID cidPrincipal;
}
