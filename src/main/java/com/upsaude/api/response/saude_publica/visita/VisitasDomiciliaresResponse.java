package com.upsaude.api.response.saude_publica.visita;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;


import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitasDomiciliaresResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private EquipeSaudeResponse equipeSaude;
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
