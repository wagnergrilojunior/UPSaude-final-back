package com.upsaude.api.response.integracao;
import com.upsaude.api.response.paciente.PacienteResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGovResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private UUID uuidRnds;
    private String idIntegracaoGov;
    private LocalDateTime dataSincronizacaoGov;
    private String ineEquipe;
    private String microarea;
    private String cnesEstabelecimentoOrigem;
    private String origemCadastro;
}
