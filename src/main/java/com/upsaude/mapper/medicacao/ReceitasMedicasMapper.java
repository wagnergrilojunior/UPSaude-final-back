package com.upsaude.mapper.medicacao;

import com.upsaude.api.request.medicacao.ReceitasMedicasRequest;
import com.upsaude.api.response.medicacao.ReceitasMedicasResponse;
import com.upsaude.dto.ReceitasMedicasDTO;
import com.upsaude.entity.medicacao.ReceitasMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, PacienteMapper.class, EstabelecimentosMapper.class, MedicacaoMapper.class})
public interface ReceitasMedicasMapper extends EntityMapper<ReceitasMedicas, ReceitasMedicasDTO> {

    @Mapping(target = "active", ignore = true)
    ReceitasMedicas toEntity(ReceitasMedicasDTO dto);

    ReceitasMedicasDTO toDTO(ReceitasMedicas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medicacoes", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    ReceitasMedicas fromRequest(ReceitasMedicasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medicacoes", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(ReceitasMedicasRequest request, @MappingTarget ReceitasMedicas entity);

    ReceitasMedicasResponse toResponse(ReceitasMedicas entity);
}
