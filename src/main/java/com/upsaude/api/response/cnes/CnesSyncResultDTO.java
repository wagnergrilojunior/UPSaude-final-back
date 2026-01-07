package com.upsaude.api.response.cnes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CnesSyncResultDTO {
    private UUID logId;
    private String status; // SUCESSO, ERRO, PARCIAL
    private OffsetDateTime dataSincronizacao;
    private Integer registrosProcessados;
    private Integer leitosSincronizados;
    private Integer equipamentosSincronizados;
    private List<String> mensagens;
}
