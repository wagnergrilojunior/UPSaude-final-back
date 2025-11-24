package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FabricantesVacinaDTO {
    private UUID id;
    private Boolean active;
}

