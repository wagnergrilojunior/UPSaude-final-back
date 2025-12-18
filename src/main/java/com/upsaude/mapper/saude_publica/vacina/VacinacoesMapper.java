package com.upsaude.mapper.saude_publica.vacina;

import com.upsaude.api.request.saude_publica.vacina.VacinacoesRequest;
import com.upsaude.api.response.saude_publica.vacina.VacinacoesResponse;
import com.upsaude.dto.saude_publica.vacina.VacinacoesDTO;
import com.upsaude.entity.saude_publica.vacina.Vacinacoes;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {FabricantesVacinaMapper.class, PacienteMapper.class, VacinasMapper.class, EstabelecimentosMapper.class})
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
    @Mapping(target = "estabelecimento", ignore = true)
    Vacinacoes fromRequest(VacinacoesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(VacinacoesRequest request, @MappingTarget Vacinacoes entity);

    VacinacoesResponse toResponse(Vacinacoes entity);
}
