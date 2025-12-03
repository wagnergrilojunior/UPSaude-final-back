package com.upsaude.api.response;

import com.upsaude.enums.StatusReceitaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitasMedicasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private MedicosResponse medico;
    private PacienteResponse paciente;
    private String numeroReceita;
    private OffsetDateTime dataPrescricao;
    private OffsetDateTime dataValidade;
    private Boolean usoContinuo;
    private String observacoes;
    private StatusReceitaEnum status;
    private String origemReceita;
    private CidDoencasResponse cidPrincipal;
}
