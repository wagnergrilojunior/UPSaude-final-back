package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoProcedimentoSimplificadoResponse;
import com.upsaude.api.response.financeiro.EstornoFinanceiroResponse;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroSimplificadoResponse;
import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class, GuiaAtendimentoAmbulatorialMapper.class })
public interface EstornoFinanceiroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "guiaAmbulatorial", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "atendimentoProcedimento", ignore = true)
    @Mapping(target = "lancamentoFinanceiroOrigem", ignore = true)
    @Mapping(target = "lancamentoFinanceiroEstorno", ignore = true)
    EstornoFinanceiro fromRequest(EstornoFinanceiroRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "guiaAmbulatorial", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "atendimentoProcedimento", ignore = true)
    @Mapping(target = "lancamentoFinanceiroOrigem", ignore = true)
    @Mapping(target = "lancamentoFinanceiroEstorno", ignore = true)
    void updateFromRequest(EstornoFinanceiroRequest request, @MappingTarget EstornoFinanceiro entity);

    EstornoFinanceiroResponse toResponse(EstornoFinanceiro entity);

    default AtendimentoProcedimentoSimplificadoResponse mapAtendimentoProcedimento(
            com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento ap) {
        if (ap == null) return null;
        try {
            return AtendimentoProcedimentoSimplificadoResponse.builder()
                    .id(ap.getId())
                    .quantidade(ap.getQuantidade())
                    .valorTotal(ap.getValorTotal())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default LancamentoFinanceiroSimplificadoResponse mapLancamentoFinanceiro(com.upsaude.entity.financeiro.LancamentoFinanceiro l) {
        if (l == null) return null;
        try {
            return LancamentoFinanceiroSimplificadoResponse.builder()
                    .id(l.getId())
                    .status(l.getStatus())
                    .dataEvento(l.getDataEvento())
                    .descricao(l.getDescricao())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

