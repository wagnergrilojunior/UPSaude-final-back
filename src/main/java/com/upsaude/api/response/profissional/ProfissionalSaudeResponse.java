package com.upsaude.api.response.profissional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalSaudeResponse {

    private UUID id;
    private String nomeCompleto;
    private String cpf;
    private String cns;
    private String rg;
    private String orgaoEmissorRg;
    private String ufEmissaoRg;
    private LocalDate dataEmissaoRg;
    private LocalDate dataNascimento;
    private String sexo;
    private String registroProfissional;
    private String ufRegistro;
    private String statusRegistro;
    private OffsetDateTime dataEmissaoRegistro;
    private OffsetDateTime dataValidadeRegistro;
    private String telefone;
    private String celular;
    private String email;
    private String emailInstitucional;
    private OffsetDateTime dataUltimaSincronizacaoCnes;
    private Boolean ativo;
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;
}
