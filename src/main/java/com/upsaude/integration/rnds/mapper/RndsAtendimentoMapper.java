package com.upsaude.integration.rnds.mapper;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.integration.rnds.dto.RndsEncounterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RndsAtendimentoMapper {

    @Mapping(target = "resourceType", constant = "Encounter")
    @Mapping(target = "status", expression = "java(mapStatus(entity.getInformacoes().getStatusAtendimento()))")
    @Mapping(target = "class_", expression = "java(mapClass(entity.getClasseAtendimento()))")
    @Mapping(target = "priority", expression = "java(mapPriority(entity.getPrioridadeAtendimento()))")
    // Outros mapeamentos seriam implementados aqui
    RndsEncounterDTO toRnds(Atendimento entity);

    default String mapStatus(com.upsaude.enums.StatusAtendimentoEnum status) {
        if (status == null)
            return "planned";
        return switch (status) {
            case AGENDADO -> "planned";
            case EM_ESPERA -> "arrived";
            case EM_ANDAMENTO -> "in-progress";
            case CONCLUIDO -> "finished";
            case CANCELADO -> "cancelled";
            default -> "planned";
        };
    }

    default RndsEncounterDTO.RndsCodingDTO mapClass(com.upsaude.enums.ClasseAtendimentoEnum classe) {
        if (classe == null)
            return null;
        return RndsEncounterDTO.RndsCodingDTO.builder()
                .system("http://terminology.hl7.org/CodeSystem/v3-ActCode")
                .code(classe.getCodigoRnds())
                .display(classe.getDescricao())
                .build();
    }

    default RndsEncounterDTO.RndsCodingDTO mapPriority(PrioridadeAtendimentoEnum prioridade) {
        if (prioridade == null)
            return null;
        return RndsEncounterDTO.RndsCodingDTO.builder()
                .system("http://terminology.hl7.org/CodeSystem/v3-ActPriority")
                .code(prioridade.getCodigoRnds())
                .display(prioridade.getDescricao())
                .build();
    }
}
