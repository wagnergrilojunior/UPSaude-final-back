package com.upsaude.dto.sistema;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDTO {
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
    private String observacoes;
}
