package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CNSValido;
import com.upsaude.validation.annotation.CPFValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Documentos básicos do profissional")
public class DocumentosBasicosProfissionalRequest {

    @CPFValido
    private String cpf;

    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @Size(max = 50, message = "Órgão emissor RG deve ter no máximo 50 caracteres")
    private String orgaoEmissorRg;

    @Size(max = 2, message = "UF emissão RG deve ter no máximo 2 caracteres")
    private String ufEmissaoRg;

    private LocalDate dataEmissaoRg;

    @CNSValido
    private String cns;
}

