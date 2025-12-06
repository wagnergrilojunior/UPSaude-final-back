package com.upsaude.api.response;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
    
    @Builder.Default
    private List<EstabelecimentoVinculoSimples> estabelecimentosVinculados = new ArrayList<>();
    
    @Getter
    @Setter
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
