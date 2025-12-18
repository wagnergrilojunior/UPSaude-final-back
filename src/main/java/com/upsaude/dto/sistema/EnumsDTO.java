package com.upsaude.dto.sistema;

import com.upsaude.api.response.EnumInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumsDTO {
    private List<EnumInfoResponse> enums;
    private Integer totalEnums;
}
