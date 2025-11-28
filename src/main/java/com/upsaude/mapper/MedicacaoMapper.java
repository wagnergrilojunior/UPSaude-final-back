package com.upsaude.mapper;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.dto.MedicacaoDTO;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.entity.Medicacao;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Medicações.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface MedicacaoMapper extends EntityMapper<Medicacao, MedicacaoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "fabricanteEntity", source = "fabricanteId", qualifiedByName = "fabricanteFromId")
    Medicacao toEntity(MedicacaoDTO dto);

    @Mapping(target = "fabricanteId", source = "fabricanteEntity.id")
    MedicacaoDTO toDTO(Medicacao entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricanteEntity", source = "fabricanteId", qualifiedByName = "fabricanteFromId")
    Medicacao fromRequest(MedicacaoRequest request);

    @Mapping(target = "fabricanteId", source = "fabricanteEntity.id")
    @Mapping(target = "fabricanteNome", source = "fabricanteEntity", qualifiedByName = "fabricanteNome")
    MedicacaoResponse toResponse(Medicacao entity);

    @Named("fabricanteFromId")
    default FabricantesMedicamento fabricanteFromId(UUID id) {
        if (id == null) return null;
        FabricantesMedicamento f = new FabricantesMedicamento();
        f.setId(id);
        return f;
    }

    @Named("fabricanteNome")
    default String fabricanteNome(FabricantesMedicamento fabricante) {
        if (fabricante == null) return null;
        return fabricante.getNome();
    }
}
