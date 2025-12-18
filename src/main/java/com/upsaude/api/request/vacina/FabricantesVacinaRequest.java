package com.upsaude.api.request.vacina;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de fabricantes vacina")
public class FabricantesVacinaRequest {
    private String nome;
    private String cnpj;
    private String pais;
    private String estado;
    private String cidade;
    private EnderecoRequest endereco;
    private String telefone;
    private String email;
    private String site;
    private String registroAnvisa;
    private String registroMs;
    private String observacoes;
}
