package com.upsaude.api.request.equipamento;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de fabricantes equipamento")
public class FabricantesEquipamentoRequest {
    private String nome;
    private String cnpj;
    private String pais;
    private String estado;
    private String cidade;
    private String endereco;
    private String telefone;
    private String email;
    private String site;
    private String registroAnvisa;
    private String observacoes;
}
