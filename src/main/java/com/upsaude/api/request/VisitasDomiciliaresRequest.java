package com.upsaude.api.request;

import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitasDomiciliaresRequest {
    private UUID paciente;
    private UUID profissional;
    private UUID equipeSaude;
    private TipoVisitaDomiciliarEnum tipoVisita;
    private OffsetDateTime dataVisita;
    private String turno;
    private Integer duracaoMinutos;
    private String endereco;
    private String bairro;
    private String cep;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String motivo;
    private String motivoNaoRealizacao;
    private String informanteNome;
    private String informanteParentesco;
    private Integer pressaoSistolica;
    private Integer pressaoDiastolica;
    private Integer glicemiaCapilar;
    private BigDecimal temperatura;
    private BigDecimal peso;
    private String procedimentosDetalhes;
    private String encaminhamentoDetalhes;
    private String desfecho;
    private OffsetDateTime dataProximaVisita;
    private String observacoes;
    private String observacoesInternas;
}
