package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de medicações continuas")
public class MedicacoesContinuasRequest {
    private String nome;
    private String dosagem;
    private String fabricante;
}
