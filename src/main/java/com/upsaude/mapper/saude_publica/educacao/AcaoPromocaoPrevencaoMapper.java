package com.upsaude.mapper.saude_publica.educacao;

import com.upsaude.api.request.saude_publica.educacao.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.saude_publica.educacao.AcaoPromocaoPrevencaoResponse;
import com.upsaude.dto.saude_publica.educacao.AcaoPromocaoPrevencaoDTO;
import com.upsaude.entity.saude_publica.educacao.AcaoPromocaoPrevencao;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.equipe.EquipeSaudeMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface AcaoPromocaoPrevencaoMapper extends EntityMapper<AcaoPromocaoPrevencao, AcaoPromocaoPrevencaoDTO> {

    @Mapping(target = "active", ignore = true)
    AcaoPromocaoPrevencao toEntity(AcaoPromocaoPrevencaoDTO dto);

    AcaoPromocaoPrevencaoDTO toDTO(AcaoPromocaoPrevencao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    AcaoPromocaoPrevencao fromRequest(AcaoPromocaoPrevencaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    void updateFromRequest(AcaoPromocaoPrevencaoRequest request, @MappingTarget AcaoPromocaoPrevencao entity);

    AcaoPromocaoPrevencaoResponse toResponse(AcaoPromocaoPrevencao entity);
}
