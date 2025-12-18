package com.upsaude.mapper.clinica.medicacao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.medicacao.MedicacaoRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacaoResponse;
import com.upsaude.dto.clinica.medicacao.MedicacaoDTO;
import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.referencia.fabricante.FabricantesMedicamentoMapper;

@Mapper(config = MappingConfig.class, uses = {FabricantesMedicamentoMapper.class, com.upsaude.mapper.embeddable.IdentificacaoMedicamentoMapper.class, com.upsaude.mapper.embeddable.DosagemAdministracaoMedicamentoMapper.class, com.upsaude.mapper.embeddable.ClassificacaoMedicamentoMapper.class, com.upsaude.mapper.embeddable.RegistroControleMedicamentoMapper.class, com.upsaude.mapper.embeddable.ContraindicacoesPrecaucoesMedicamentoMapper.class, com.upsaude.mapper.embeddable.ConservacaoArmazenamentoMedicamentoMapper.class})
public interface MedicacaoMapper extends EntityMapper<Medicacao, MedicacaoDTO> {

    @Mapping(target = "active", ignore = true)
    Medicacao toEntity(MedicacaoDTO dto);

    MedicacaoDTO toDTO(Medicacao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricanteEntity", ignore = true)
    Medicacao fromRequest(MedicacaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricanteEntity", ignore = true)
    void updateFromRequest(MedicacaoRequest request, @MappingTarget Medicacao entity);

    MedicacaoResponse toResponse(Medicacao entity);
}
