package com.upsaude.dto.sistema;

import java.util.List;

import com.upsaude.api.response.sistema.EnumInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumsDTO {
    private List<EnumInfoResponse> enums;
    private Integer totalEnums;
}
