package com.upsaude.api.response.estabelecimento;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;


import com.upsaude.enums.TipoUsuarioSistemaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
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
public class UsuarioEstabelecimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UsuariosSistemaResponse usuario;
    private EstabelecimentosResponse estabelecimento;

    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private TipoUsuarioSistemaEnum tipoUsuario;
}
