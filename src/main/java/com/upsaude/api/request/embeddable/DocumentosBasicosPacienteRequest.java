package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.annotation.CNSValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Documentos básicos do paciente")
public class DocumentosBasicosPacienteRequest {

    @CPFValido
    private String cpf;

    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @CNSValido
    private String cns;

    @Size(max = 10, message = "Órgão emissor RG deve ter no máximo 10 caracteres")
    private String orgaoEmissorRg;

    @Size(max = 2, message = "UF emissor RG deve ter no máximo 2 caracteres")
    private String ufEmissorRg;
}

