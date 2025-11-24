package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasCronicasDTO {
    private UUID id;
    private Boolean active;
}

