package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.PrevencaoTratamentoAlergiaRequest;
import com.upsaude.api.response.embeddable.PrevencaoTratamentoAlergiaResponse;
import com.upsaude.dto.embeddable.PrevencaoTratamentoAlergiaDTO;
import com.upsaude.entity.embeddable.PrevencaoTratamentoAlergia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface PrevencaoTratamentoAlergiaMapper {
    PrevencaoTratamentoAlergia toEntity(PrevencaoTratamentoAlergiaRequest request);
    PrevencaoTratamentoAlergiaResponse toResponse(PrevencaoTratamentoAlergia entity);
    void updateFromRequest(PrevencaoTratamentoAlergiaRequest request, @MappingTarget PrevencaoTratamentoAlergia entity);

    PrevencaoTratamentoAlergia toEntity(PrevencaoTratamentoAlergiaDTO dto);
    PrevencaoTratamentoAlergiaDTO toDTO(PrevencaoTratamentoAlergia entity);
    void updateFromDTO(PrevencaoTratamentoAlergiaDTO dto, @MappingTarget PrevencaoTratamentoAlergia entity);
}
