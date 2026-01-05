package com.upsaude.api.request.sistema.integracao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.validation.annotation.CNESValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de integracao gov")
public class IntegracaoGovRequest {
    private UUID paciente;
    private UUID uuidRnds;

    @Size(max = 100, message = "ID de integração deve ter no máximo 100 caracteres")
    private String idIntegracaoGov;

    private LocalDateTime dataSincronizacaoGov;
    private String ineEquipe;
    private String microarea;
    @CNESValido
    private String cnesEstabelecimentoOrigem;

    @Size(max = 30, message = "Origem do cadastro deve ter no máximo 30 caracteres")
    private String origemCadastro;
}
