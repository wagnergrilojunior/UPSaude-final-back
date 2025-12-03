package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID userId;
    private ProfissionaisSaudeResponse profissionalSaude;
    private MedicosResponse medico;
    private PacienteResponse paciente;
    private Boolean adminTenant;
    private String tipoVinculo; // MEDICO, PROFISSIONAL, PACIENTE, OUTROS
    private String nomeExibicao;
    private String username;
    private String email;
    private String fotoUrl;
    
    // Informações do Tenant/Organização
    private UUID tenantId;
    private String tenantNome;
    private String tenantSlug;
    
    // Tipo de usuário (baseado em vínculos) - ENUM
    private TipoUsuarioSistemaEnum tipoUsuario;
    
    // Vínculos com estabelecimentos (DTO simplificado para evitar referência circular)
    private java.util.List<EstabelecimentoVinculoSimples> estabelecimentosVinculados;
    
    /**
     * DTO simplificado para vínculo de estabelecimento.
     * Sem referência ao usuário para evitar StackOverflow em serialização JSON.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoVinculoSimples {
        private UUID id;
        private UUID estabelecimentoId;
        private String estabelecimentoNome;
        private TipoUsuarioSistemaEnum tipoUsuario; // Papel do usuário neste estabelecimento
        private Boolean active;
    }
}
