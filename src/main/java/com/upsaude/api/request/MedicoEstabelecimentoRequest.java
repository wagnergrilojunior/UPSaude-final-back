package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.util.converter.TipoVinculoProfissionalEnumDeserializer;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de médico estabelecimento")
public class MedicoEstabelecimentoRequest {
    private UUID medico;
    private UUID estabelecimento;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    @JsonDeserialize(using = TipoVinculoProfissionalEnumDeserializer.class)
    private TipoVinculoProfissionalEnum tipoVinculo;
    private Integer cargaHorariaSemanal;
    private BigDecimal salario;
    private String numeroMatricula;
    private String setorDepartamento;
    private String cargoFuncao;
    private String observacoes;
}
