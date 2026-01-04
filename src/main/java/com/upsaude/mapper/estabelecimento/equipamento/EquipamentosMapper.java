package com.upsaude.mapper.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.api.response.estabelecimento.equipamento.EquipamentosResponse;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.DadosIdentificacaoEquipamentoMapper;
import com.upsaude.mapper.embeddable.DescricoesEquipamentoMapper;
import com.upsaude.mapper.embeddable.EspecificacoesTecnicasEquipamentoMapper;
import com.upsaude.mapper.embeddable.ManutencaoCalibracaoEquipamentoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {
    FabricantesEquipamentoMapper.class,
    DadosIdentificacaoEquipamentoMapper.class,
    EspecificacoesTecnicasEquipamentoMapper.class,
    ManutencaoCalibracaoEquipamentoMapper.class,
    DescricoesEquipamentoMapper.class
})
public interface EquipamentosMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    Equipamentos fromRequest(EquipamentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    void updateFromRequest(EquipamentosRequest request, @MappingTarget Equipamentos entity);

    EquipamentosResponse toResponse(Equipamentos entity);
}
