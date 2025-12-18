package com.upsaude.api.response.paciente;
import com.upsaude.api.response.paciente.PacienteResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosClinicosBasicosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private Boolean gestante;
    private Boolean fumante;
    private Boolean alcoolista;
    private Boolean usuarioDrogas;
    private Boolean historicoViolencia;
    private Boolean acompanhamentoPsicossocial;
}
