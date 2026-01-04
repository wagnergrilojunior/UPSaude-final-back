package com.upsaude.api.response.estabelecimento.equipamento;

import com.upsaude.api.response.geral.EnderecoResponse;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FabricantesEquipamentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String cnpj;
    private EnderecoResponse endereco;
    private String telefone;
    private String email;
    private String site;
}
