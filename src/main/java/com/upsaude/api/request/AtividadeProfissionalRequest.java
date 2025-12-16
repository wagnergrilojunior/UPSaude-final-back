package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import com.upsaude.util.converter.TipoAtividadeProfissionalEnumDeserializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de atividade profissional")
public class AtividadeProfissionalRequest {
    private UUID profissional;
    private UUID medico;
    private UUID paciente;
    private UUID atendimento;
    private UUID cirurgia;
    private UUID estabelecimento;
    private OffsetDateTime dataHora;
    private LocalDate dataAtividade;
    @JsonDeserialize(using = TipoAtividadeProfissionalEnumDeserializer.class)
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
