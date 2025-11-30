package com.upsaude.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response que representa todas as informações de um enum específico.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um enum completo com todos os seus valores")
public class EnumInfoResponse {
    
    @Schema(description = "Nome formatado do enum para exibição", example = "Sexo")
    private String nomeEnum;
    
    @Schema(description = "Nome da classe Java do enum", example = "SexoEnum")
    private String nomeClasse;
    
    @Schema(description = "Lista de todos os valores possíveis deste enum")
    private List<EnumItemResponse> valores;
}

