package com.upsaude.api.response;

import com.upsaude.enums.FrequenciaMedicacaoEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para Medicações de Paciente.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoPacienteResponse {
    private UUID id;
    private UUID pacienteId;
    private UUID medicacaoId;
    private String medicacaoNomeComercial;
    private String medicacaoPrincipioAtivo;
    private String dose;
    private FrequenciaMedicacaoEnum frequencia;
    private ViaAdministracaoEnum via;
    private UUID cidRelacionadoId;
    private String cidRelacionadoCodigo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean medicacaoAtiva;
    private String observacoes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

