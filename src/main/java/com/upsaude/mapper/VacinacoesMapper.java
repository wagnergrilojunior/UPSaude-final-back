package com.upsaude.mapper;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.dto.VacinacoesDTO;
import com.upsaude.entity.Vacinacoes;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {FabricantesVacinaMapper.class, PacienteMapper.class, VacinasMapper.class})
public interface VacinacoesMapper extends EntityMapper<Vacinacoes, VacinacoesDTO> {

    @Mapping(target = "active", ignore = true)
    Vacinacoes toEntity(VacinacoesDTO dto);

    VacinacoesDTO toDTO(Vacinacoes entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    Vacinacoes fromRequest(VacinacoesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    void updateFromRequest(VacinacoesRequest request, @MappingTarget Vacinacoes entity);

    VacinacoesResponse toResponse(Vacinacoes entity);
}
