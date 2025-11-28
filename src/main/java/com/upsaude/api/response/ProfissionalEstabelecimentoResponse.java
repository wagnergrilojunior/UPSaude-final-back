package com.upsaude.api.response;

import com.upsaude.enums.TipoVinculoProfissionalEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalEstabelecimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID profissionalId;
    private String profissionalNome;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private TipoVinculoProfissionalEnum tipoVinculo;
    private Integer cargaHorariaSemanal;
    private BigDecimal salario;
    private String numeroMatricula;
    private String setorDepartamento;
    private String cargoFuncao;
    private String observacoes;
}

