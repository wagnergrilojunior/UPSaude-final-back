package com.upsaude.api.response.clinica.atendimento;

import java.time.LocalDate;
import java.util.UUID;
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
public class PacienteAtendimentoResponse {
    private UUID id;
    private String nomeCompleto;
    private String cpf;
    private String cns;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
}

