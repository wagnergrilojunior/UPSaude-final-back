package com.upsaude.api.response.sistema.usuario;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoVinculoSimplificadoResponse {
    private UUID id;
    private UUID tenantId;
    private UUID estabelecimentoId;
    private UUID estabelecimentoTenantId;
    private UUID estabelecimentoEnderecoId;
    private UUID estabelecimentoEnderecoEstadoId;
    private UUID estabelecimentoEnderecoCidadeId;
    private TipoUsuarioSistemaEnum tipoUsuario;
}
