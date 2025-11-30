package com.upsaude.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response que representa todos os enums do sistema.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo todos os enums do sistema")
public class EnumsResponse {
    
    @Schema(description = "Lista de todos os enums disponíveis no sistema")
    private List<EnumInfoResponse> enums;
    
    @Schema(description = "Total de enums retornados", example = "70")
    private Integer totalEnums;
}

