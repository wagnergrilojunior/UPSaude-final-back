package com.upsaude.api.response.cnes;

import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CnesSincronizacaoResponse {
    private UUID id;
    private TipoEntidadeCnesEnum tipoEntidade;
    private UUID entidadeId;
    private String codigoIdentificador;
    private String competencia;
    private StatusSincronizacaoEnum status;
    private OffsetDateTime dataSincronizacao;
    private OffsetDateTime dataFim;
    private Integer registrosInseridos;
    private Integer registrosAtualizados;
    private Integer registrosErro;
    private String mensagemErro;
    private String detalhesErro;
    private UUID estabelecimentoId;
    private Object entidade;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
