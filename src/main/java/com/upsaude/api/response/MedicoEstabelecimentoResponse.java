package com.upsaude.api.response;

import com.upsaude.enums.TipoVinculoProfissionalEnum;
import java.math.BigDecimal;
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
public class MedicoEstabelecimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private MedicosResponse medico;
    private EstabelecimentosResponse estabelecimento;
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
