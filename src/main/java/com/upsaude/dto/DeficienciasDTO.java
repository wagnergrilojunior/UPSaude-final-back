package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Deficiências.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasDTO {
    private UUID id;
    private Boolean active;
}

