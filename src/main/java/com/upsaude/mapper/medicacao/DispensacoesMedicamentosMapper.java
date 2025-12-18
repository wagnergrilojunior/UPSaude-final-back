package com.upsaude.mapper.medicacao;

import com.upsaude.api.request.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.medicacao.DispensacoesMedicamentosResponse;
import com.upsaude.dto.DispensacoesMedicamentosDTO;
import com.upsaude.entity.medicacao.DispensacoesMedicamentos;
import com.upsaude.entity.medicacao.Medicacao;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicacaoMapper.class, PacienteMapper.class})
public interface DispensacoesMedicamentosMapper extends EntityMapper<DispensacoesMedicamentos, DispensacoesMedicamentosDTO> {

    @Mapping(target = "active", ignore = true)
    DispensacoesMedicamentos toEntity(DispensacoesMedicamentosDTO dto);

    DispensacoesMedicamentosDTO toDTO(DispensacoesMedicamentos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DispensacoesMedicamentos fromRequest(DispensacoesMedicamentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DispensacoesMedicamentosRequest request, @MappingTarget DispensacoesMedicamentos entity);

    DispensacoesMedicamentosResponse toResponse(DispensacoesMedicamentos entity);
}
