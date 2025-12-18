package com.upsaude.api.response.saude_publica.vacina;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.api.response.geral.EnderecoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FabricantesVacinaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String cnpj;
    private String pais;
    private String estado;
    private String cidade;
    private EnderecoResponse endereco;
    private String telefone;
    private String email;
    private String site;
    private String registroAnvisa;
    private String registroMs;
    private String observacoes;
}
