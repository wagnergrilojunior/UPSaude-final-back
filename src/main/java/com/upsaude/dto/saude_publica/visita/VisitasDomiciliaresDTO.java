package com.upsaude.dto.saude_publica.visita;

import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.profissional.equipe.EquipeSaudeDTO;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitasDomiciliaresDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private EquipeSaudeDTO equipeSaude;
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
