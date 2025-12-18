package com.upsaude.api.response.sistema;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.upsaude.api.response.geral.EnderecoResponse;

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
public class TenantResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String slug;
    private String descricao;
    private String urlLogo;
    private String metadados;
    private Boolean ativo;
    private String cnpj;
    private String cnes;
    private String tipoInstituicao;
    private String telefone;
    private String email;
    private String site;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String responsavelNome;
    private String responsavelCpf;
    private String responsavelTelefone;
    private String horarioFuncionamento;

    @Builder.Default
    private List<EnderecoResponse> enderecos = new ArrayList<>();

    private String observacoes;
}
