package com.upsaude.api.request.clinica.cirurgia;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusCirurgiaEnum;
import com.upsaude.util.converter.StatusCirurgiaEnumDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de cirurgia")
public class CirurgiaRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @NotNull(message = "Cirurgião principal é obrigatório")
    private UUID cirurgiaoPrincipal;

    private UUID medicoCirurgiao;
    private UUID especialidade;
    private UUID convenio;
    private UUID diagnosticoPrincipal;

    @NotBlank(message = "Descrição da cirurgia é obrigatória")
    private String descricao;

    @NotNull(message = "Data e hora prevista são obrigatórias")
    private OffsetDateTime dataHoraPrevista;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoPrevistaMinutos;
    private Integer duracaoRealMinutos;

    @Size(max = 100, message = "Sala cirúrgica deve ter no máximo 100 caracteres")
    private String salaCirurgica;

    @Size(max = 100, message = "Leito centro cirúrgico deve ter no máximo 100 caracteres")
    private String leitoCentroCirurgico;

    @NotNull(message = "Status da cirurgia é obrigatório")
    @JsonDeserialize(using = StatusCirurgiaEnumDeserializer.class)
    private StatusCirurgiaEnum status;
    private BigDecimal valorCirurgia;
    private BigDecimal valorMaterial;
    private BigDecimal valorTotal;
    private String observacoesPreOperatorio;
    private String observacoesPosOperatorio;
    private String observacoes;
    private String observacoesInternas;

    @Builder.Default
    @Schema(description = "Lista de IDs dos procedimentos SIGTAP associados à cirurgia")
    private List<UUID> procedimentos = new ArrayList<>();

    @Builder.Default
    @Schema(description = "Lista de IDs dos profissionais de saúde da equipe cirúrgica")
    private List<UUID> equipe = new ArrayList<>();
}
