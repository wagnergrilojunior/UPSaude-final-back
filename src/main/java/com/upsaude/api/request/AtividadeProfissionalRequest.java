package com.upsaude.api.request;

import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeProfissionalRequest {
    private UUID profissional;
    private UUID medico;
    private UUID paciente;
    private UUID atendimento;
    private UUID cirurgia;
    private OffsetDateTime dataHora;
    private LocalDate dataAtividade;
    private TipoAtividadeProfissionalEnum tipoAtividade;
    private String descricao;
    private Integer duracaoMinutos;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private String localRealizacao;
    private String setor;
    private Integer quantidadeAtendimentos;
    private String observacoes;
    private String observacoesInternas;
}
