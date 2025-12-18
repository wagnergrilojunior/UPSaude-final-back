package com.upsaude.mapper.profissional.medicao;

import com.upsaude.api.request.profissional.medicao.MedicaoClinicaRequest;
import com.upsaude.api.response.profissional.medicao.MedicaoClinicaResponse;
import com.upsaude.dto.profissional.medicao.MedicaoClinicaDTO;
import com.upsaude.entity.profissional.medicao.MedicaoClinica;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;
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
