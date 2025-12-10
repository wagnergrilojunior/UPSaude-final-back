package com.upsaude.api.request;

import com.upsaude.api.request.EnderecoRequest;
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
    private UUID convenio;
    private String numeroCarteirinha;
    private LocalDate dataValidadeCarteirinha;
    private String observacoes;
    private UUID estabelecimentoId;
    private List<UUID> enderecosIds;
    private List<EnderecoRequest> enderecos;
    private List<UUID> deficiencias;
    private List<UUID> medicacoes;
    private List<UUID> alergias;
    private List<UUID> doencas;
    private UUID dadosSociodemograficos;
    private UUID dadosClinicosBasicos;
    private UUID responsavelLegal;
    private UUID lgpdConsentimento;
    private UUID integracaoGov;
}

