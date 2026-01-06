package com.upsaude.api.response.clinica.prontuario;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.referencia.cid.Cid10SubcategoriaResponse;
import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse;
import java.time.OffsetDateTime;
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
public class VacinacaoPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID vacinaId;
    private ProfissionaisSaudeResponse profissionalAplicador;
    private OffsetDateTime dataAplicacao;
    private Integer numeroDose;
    private String localAplicacao;
    private String lote;
    private String reacaoAdversa;
    private String observacoes;
    private SigtapProcedimentoResponse procedimento;
    private Cid10SubcategoriaResponse diagnosticoRelacionado;
}

