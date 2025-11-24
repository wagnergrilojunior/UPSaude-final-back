package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentosOdontologicosDTO {
    private UUID id;
    private Boolean active;
}

