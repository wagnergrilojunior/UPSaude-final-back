package com.upsaude.dto.vacina;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FabricantesVacinaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String cnpj;
    private String pais;
    private String estado;
    private String cidade;
    private EnderecoDTO endereco;
    private String telefone;
    private String email;
    private String site;
    private String registroAnvisa;
    private String registroMs;
    private String observacoes;
}
