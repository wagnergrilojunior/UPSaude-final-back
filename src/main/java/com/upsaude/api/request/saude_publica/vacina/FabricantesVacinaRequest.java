package com.upsaude.api.request.saude_publica.vacina;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.upsaude.api.request.paciente.EnderecoRequest;


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
