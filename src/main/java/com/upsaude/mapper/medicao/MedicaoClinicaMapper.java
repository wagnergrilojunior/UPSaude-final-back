package com.upsaude.mapper.medicao;

import com.upsaude.api.request.medicao.MedicaoClinicaRequest;
import com.upsaude.api.response.medicao.MedicaoClinicaResponse;
import com.upsaude.dto.MedicaoClinicaDTO;
import com.upsaude.entity.medicao.MedicaoClinica;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface MedicaoClinicaMapper extends EntityMapper<MedicaoClinica, MedicaoClinicaDTO> {

    @Mapping(target = "active", ignore = true)
    MedicaoClinica toEntity(MedicaoClinicaDTO dto);

    MedicaoClinicaDTO toDTO(MedicaoClinica entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    MedicaoClinica fromRequest(MedicaoClinicaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(MedicaoClinicaRequest request, @MappingTarget MedicaoClinica entity);

    MedicaoClinicaResponse toResponse(MedicaoClinica entity);
}
