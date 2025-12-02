package com.upsaude.api.request;

import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequest {
    private String nomeCompleto;
    private String cpf;
    private String rg;
    private String cns;
    private LocalDate dataNascimento;
    private SexoEnum sexo;
    private EstadoCivilEnum estadoCivil;
    private String telefone;
    private String email;
    private String nomeMae;
    private String nomePai;
    private String responsavelNome;
    private String responsavelCpf;
    private String responsavelTelefone;
    private String enderecoJson;
    private String contatoJson;
    private String informacoesAdicionaisJson;
    private UUID convenioId;
    private String numeroCarteirinha;
    private LocalDate dataValidadeCarteirinha;
    private String observacoes;
    private List<UUID> enderecosIds;
}

