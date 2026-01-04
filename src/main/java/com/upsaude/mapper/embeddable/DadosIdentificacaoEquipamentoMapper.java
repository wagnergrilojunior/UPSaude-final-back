package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoEquipamentoRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoEquipamentoResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoEquipamentoMapper {
    DadosIdentificacaoEquipamento toEntity(DadosIdentificacaoEquipamentoRequest request);
    DadosIdentificacaoEquipamentoResponse toResponse(DadosIdentificacaoEquipamento entity);
    void updateFromRequest(DadosIdentificacaoEquipamentoRequest request, @MappingTarget DadosIdentificacaoEquipamento entity);
}

