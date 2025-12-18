package com.upsaude.mapper.cirurgia;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.api.request.cirurgia.CirurgiaRequest;
import com.upsaude.api.response.cirurgia.CirurgiaResponse;
import com.upsaude.dto.CirurgiaDTO;
import com.upsaude.entity.cirurgia.Cirurgia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface CirurgiaMapper extends EntityMapper<Cirurgia, CirurgiaDTO> {

    @Mapping(target = "active", ignore = true)
    Cirurgia toEntity(CirurgiaDTO dto);

    CirurgiaDTO toDTO(Cirurgia entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    Cirurgia fromRequest(CirurgiaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    void updateFromRequest(CirurgiaRequest request, @MappingTarget Cirurgia entity);

    // Evita ciclos/recursões indiretas via PacienteResponse/ResponsavelLegalResponse e grafos grandes.
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    CirurgiaResponse toResponse(Cirurgia entity);
}
