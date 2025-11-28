package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Medicações de Paciente.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoPacienteDTO {
    private UUID id;
    private Boolean active;
}

