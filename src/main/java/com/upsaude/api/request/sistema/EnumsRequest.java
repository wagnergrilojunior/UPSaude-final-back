package com.upsaude.api.request.sistema;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de enums")
public class EnumsRequest {
    private List<UUID> enums;
    private Integer totalEnums;
}
