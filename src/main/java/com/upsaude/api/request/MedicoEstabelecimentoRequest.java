package com.upsaude.api.request;

import com.upsaude.enums.TipoVinculoProfissionalEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoEstabelecimentoRequest {
    private UUID medico;
    private UUID estabelecimento;
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
