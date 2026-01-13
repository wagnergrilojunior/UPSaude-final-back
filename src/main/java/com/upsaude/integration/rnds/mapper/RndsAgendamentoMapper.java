package com.upsaude.integration.rnds.mapper;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.integration.rnds.dto.RndsAppointmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface RndsAgendamentoMapper {

    @Mapping(target = "resourceType", constant = "Appointment")
    @Mapping(target = "status", expression = "java(mapStatus(entity.getStatus()))")
    @Mapping(target = "appointmentType", expression = "java(entity.getTipoAgendamento() != null ? entity.getTipoAgendamento().getCodigoRnds() : null)")
    @Mapping(target = "serviceCategory", source = "categoriaServico")
    @Mapping(target = "serviceType", expression = "java(entity.getTipoServico() != null ? entity.getTipoServico().name() : null)")
    @Mapping(target = "description", source = "observacoesAgendamento")
    @Mapping(target = "created", expression = "java(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)")
    // Mapeamentos complexos seriam feitos em métodos default
    RndsAppointmentDTO toRnds(Agendamento entity);

    default String mapStatus(com.upsaude.enums.StatusAgendamentoEnum status) {
        if (status == null)
            return "pending";
        return switch (status) {
            case AGENDADO -> "booked";
            case CONFIRMADO -> "booked";
            case CONCLUIDO -> "fulfilled";
            case CANCELADO -> "cancelled";
            case FALTA -> "noshow";
            default -> "proposed";
        };
    }
}
