package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Medicações.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoDTO {
    private UUID id;
    private Boolean active;
}

