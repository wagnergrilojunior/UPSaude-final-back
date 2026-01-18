package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.embeddable.AnamneseAtendimentoResponse;
import com.upsaude.api.response.embeddable.ClassificacaoRiscoAtendimentoResponse;
import com.upsaude.api.response.convenio.ConvenioSimplificadoResponse;
import com.upsaude.api.response.embeddable.DiagnosticoAtendimentoResponse;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeSimplificadoResponse;
import com.upsaude.api.response.embeddable.InformacoesAtendimentoResponse;
import com.upsaude.api.response.embeddable.ProcedimentosRealizadosAtendimentoResponse;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class AtendimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteAtendimentoResponse paciente;
    private ProfissionalAtendimentoResponse profissional;
    private EquipeSaudeSimplificadoResponse equipeSaude;
    private ConvenioSimplificadoResponse convenio;
    private InformacoesAtendimentoResponse informacoes;
    private AnamneseAtendimentoResponse anamnese;
    private DiagnosticoAtendimentoResponse diagnostico;
    private ProcedimentosRealizadosAtendimentoResponse procedimentosRealizados;
    private ClassificacaoRiscoAtendimentoResponse classificacaoRisco;
    private String anotacoes;
    private String observacoesInternas;

    
    private com.upsaude.enums.ClasseAtendimentoEnum classeAtendimento;
    private String tipoAtendimentoDetalhado;
    private com.upsaude.enums.PrioridadeAtendimentoEnum prioridadeAtendimento;
    private String motivoAtendimento;
    private String diagnosticosAdmissao;
    private String dadosInternacao;
    private String periodoReal;

    private ConsultaResponse consulta;
    
    private UUID estabelecimentoId;
    private UUID tenantId;
    private UUID enderecoId;

    
    private CompetenciaFinanceiraResponse competenciaFinanceira;

    @Builder.Default
    private List<AtendimentoProcedimentoResponse> procedimentos = new ArrayList<>();
}
