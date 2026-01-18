package com.upsaude.api.response.sistema;

import com.upsaude.api.response.sistema.enums.EnumInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response com informações de enums")
public class EnumsResponse {
    @Schema(description = "Lista de informações de enums")
    private List<EnumInfoResponse> enums;
}
