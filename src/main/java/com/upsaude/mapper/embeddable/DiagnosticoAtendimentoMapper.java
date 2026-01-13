package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DiagnosticoAtendimentoRequest;
import com.upsaude.api.response.embeddable.DiagnosticoAtendimentoResponse;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.mapper.clinica.ClinicalReferenceMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.diagnostico.Ciap2Mapper;
import com.upsaude.mapper.referencia.cid.Cid10SubcategoriaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { ClinicalReferenceMapper.class, Cid10SubcategoriaMapper.class,
        Ciap2Mapper.class })
public interface DiagnosticoAtendimentoMapper {
    @Mapping(target = "mainCid10", source = "mainCid10Id")
    @Mapping(target = "mainCiap2", source = "mainCiap2Id")
    DiagnosticoAtendimento toEntity(DiagnosticoAtendimentoRequest request);

    DiagnosticoAtendimentoResponse toResponse(DiagnosticoAtendimento entity);

    @Mapping(target = "mainCid10", source = "mainCid10Id")
    @Mapping(target = "mainCiap2", source = "mainCiap2Id")
    void updateFromRequest(DiagnosticoAtendimentoRequest request, @MappingTarget DiagnosticoAtendimento entity);
}
