package com.upsaude.api.request;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoRequest {
    private UUID paciente;
    private UUID profissional;
    private UUID especialidade;
    private UUID equipeSaude;
    private UUID convenio;
    private InformacoesAtendimento informacoes;
    private AnamneseAtendimento anamnese;
    private DiagnosticoAtendimento diagnostico;
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;
    private ClassificacaoRiscoAtendimento classificacaoRisco;
    private UUID cidPrincipal;
    private String anotacoes;
    private String observacoesInternas;
}
