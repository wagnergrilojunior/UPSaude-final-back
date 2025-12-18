package com.upsaude.mapper.vacina;

import com.upsaude.api.request.vacina.VacinasRequest;
import com.upsaude.api.response.vacina.VacinasResponse;
import com.upsaude.dto.VacinasDTO;
import com.upsaude.entity.vacina.Vacinas;
import com.upsaude.entity.vacina.FabricantesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {FabricantesVacinaMapper.class, com.upsaude.mapper.embeddable.EsquemaVacinalMapper.class, com.upsaude.mapper.embeddable.IdadeAplicacaoVacinaMapper.class, com.upsaude.mapper.embeddable.ContraindicacoesVacinaMapper.class, com.upsaude.mapper.embeddable.ConservacaoVacinaMapper.class, com.upsaude.mapper.embeddable.ComposicaoVacinaMapper.class, com.upsaude.mapper.embeddable.EficaciaVacinaMapper.class, com.upsaude.mapper.embeddable.ReacoesAdversasVacinaMapper.class, com.upsaude.mapper.embeddable.CalendarioVacinalMapper.class, com.upsaude.mapper.embeddable.IntegracaoGovernamentalVacinaMapper.class})
public interface VacinasMapper extends EntityMapper<Vacinas, VacinasDTO> {

    @Mapping(target = "active", ignore = true)
    Vacinas toEntity(VacinasDTO dto);

    VacinasDTO toDTO(Vacinas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    Vacinas fromRequest(VacinasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    void updateFromRequest(VacinasRequest request, @MappingTarget Vacinas entity);

    VacinasResponse toResponse(Vacinas entity);
}
