package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasPacienteDTO {
    private UUID id;
    private Boolean active;
}

