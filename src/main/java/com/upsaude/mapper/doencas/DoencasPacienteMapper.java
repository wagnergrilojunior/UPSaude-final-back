package com.upsaude.mapper.doencas;

import com.upsaude.api.request.doencas.DoencasPacienteRequest;
import com.upsaude.api.response.doencas.DoencasPacienteResponse;
import com.upsaude.dto.DoencasPacienteDTO;
import com.upsaude.entity.doencas.DoencasPaciente;
import com.upsaude.entity.doencas.Doencas;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {DoencasMapper.class, PacienteMapper.class, com.upsaude.mapper.embeddable.DiagnosticoDoencaPacienteMapper.class, com.upsaude.mapper.embeddable.AcompanhamentoDoencaPacienteMapper.class, com.upsaude.mapper.embeddable.TratamentoAtualDoencaPacienteMapper.class})
public interface DoencasPacienteMapper extends EntityMapper<DoencasPaciente, DoencasPacienteDTO> {

    @Mapping(target = "active", ignore = true)
    DoencasPaciente toEntity(DoencasPacienteDTO dto);

    DoencasPacienteDTO toDTO(DoencasPaciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "doenca", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DoencasPaciente fromRequest(DoencasPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "doenca", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DoencasPacienteRequest request, @MappingTarget DoencasPaciente entity);

    @Mapping(target = "paciente", ignore = true)
    DoencasPacienteResponse toResponse(DoencasPaciente entity);
}
