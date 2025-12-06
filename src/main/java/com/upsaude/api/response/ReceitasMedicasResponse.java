package com.upsaude.api.response;

import com.upsaude.enums.StatusReceitaEnum;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    
    @Builder.Default
    private List<MedicacaoResponse> medicacoes = new ArrayList<>();
}
