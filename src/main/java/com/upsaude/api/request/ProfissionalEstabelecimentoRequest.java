package com.upsaude.api.request;

import com.upsaude.enums.TipoVinculoProfissionalEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ProfissionalEstabelecimentoRequest {
    @NotNull(message = "Profissional é obrigatório")
    private UUID profissionalId;

    @NotNull(message = "Estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    @NotNull(message = "Data de início do vínculo é obrigatória")
    private OffsetDateTime dataInicio;

    private OffsetDateTime dataFim;

    @NotNull(message = "Tipo de vínculo é obrigatório")
    private TipoVinculoProfissionalEnum tipoVinculo;

    private Integer cargaHorariaSemanal;

    private BigDecimal salario;

    @Size(max = 50, message = "Número de matrícula deve ter no máximo 50 caracteres")
    private String numeroMatricula;

    @Size(max = 255, message = "Setor/Departamento deve ter no máximo 255 caracteres")
    private String setorDepartamento;

    @Size(max = 255, message = "Cargo/Função deve ter no máximo 255 caracteres")
    private String cargoFuncao;

    private String observacoes;
}

