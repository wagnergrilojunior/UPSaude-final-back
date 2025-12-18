package com.upsaude.mapper.saude_publica.vacina;

import com.upsaude.api.request.saude_publica.vacina.VacinasRequest;
import com.upsaude.api.response.saude_publica.vacina.VacinasResponse;
import com.upsaude.dto.saude_publica.vacina.VacinasDTO;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.entity.saude_publica.vacina.FabricantesVacina;
import com.upsaude.mapper.EntityMapper;
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
