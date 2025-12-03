package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEstabelecimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UsuariosSistemaResponse usuario;
    private EstabelecimentosResponse estabelecimento;
    
    // Campos simplificados para uso em listas
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private TipoUsuarioSistemaEnum tipoUsuario; // Papel do usuário neste estabelecimento
}
