package com.upsaude.mapper.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaMedicoRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaMedicoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaMedico;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingConfig.class, uses = {ConsultaMapper.class})
public interface EquipeCirurgicaMedicoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeCirurgica", ignore = true)
    @Mapping(target = "medico", ignore = true)
    EquipeCirurgicaMedico fromRequest(EquipeCirurgicaMedicoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeCirurgica", ignore = true)
    @Mapping(target = "medico", ignore = true)
    void updateFromRequest(EquipeCirurgicaMedicoRequest request, @MappingTarget EquipeCirurgicaMedico entity);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "medico", source = "medico", qualifiedByName = "mapMedicoSimplificadoEquipeMedico")
    EquipeCirurgicaMedicoResponse toResponse(EquipeCirurgicaMedico entity);

    @Named("mapMedicoSimplificadoEquipeMedico")
    default MedicoConsultaResponse mapMedicoSimplificadoEquipeMedico(Medicos medico) {
        if (medico == null) {
            return null;
        }
        ConsultaMapper mapper = Mappers.getMapper(ConsultaMapper.class);
        return mapper.mapMedicoSimplificado(medico);
    }
}

