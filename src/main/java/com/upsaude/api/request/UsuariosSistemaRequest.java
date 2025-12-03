package com.upsaude.api.request;

import java.util.List;
import java.util.UUID;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaRequest {
    private UUID userId;
    private UUID profissionalSaude;
    private UUID medico;
    private UUID paciente;
    private UUID tenantId;
    private Boolean adminTenant;
    private String tipoVinculo; // MEDICO, PROFISSIONAL, PACIENTE, OUTROS
    private String nomeExibicao;
    private String username;
    private String email;
    private String senha;
    private String fotoUrl;
    
    // NOVO: Lista de vínculos com estabelecimentos (estabelecimento + papel)
    private List<EstabelecimentoVinculoRequest> estabelecimentos;
    
    // DEPRECATED: Manter para compatibilidade com código antigo
    @Deprecated
    private List<UUID> estabelecimentosIds;
    
    /**
     * DTO interno para representar vínculo de estabelecimento com papel.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoVinculoRequest {
        private UUID estabelecimentoId;
        private TipoUsuarioSistemaEnum tipoUsuario; // Papel do usuário neste estabelecimento
    }
}
