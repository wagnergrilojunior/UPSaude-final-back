package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.RegistroANSConvenioRequest;
import com.upsaude.api.response.embeddable.RegistroANSConvenioResponse;
import com.upsaude.entity.embeddable.RegistroANSConvenio;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface RegistroANSConvenioMapper {
    RegistroANSConvenio toEntity(RegistroANSConvenioRequest request);
    RegistroANSConvenioResponse toResponse(RegistroANSConvenio entity);
    void updateFromRequest(RegistroANSConvenioRequest request, @MappingTarget RegistroANSConvenio entity);

}
