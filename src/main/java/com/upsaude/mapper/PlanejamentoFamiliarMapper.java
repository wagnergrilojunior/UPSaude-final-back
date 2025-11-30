package com.upsaude.mapper;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.PlanejamentoFamiliarResponse;
import com.upsaude.dto.PlanejamentoFamiliarDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre PlanejamentoFamiliar entity, DTO, Request e Response.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface PlanejamentoFamiliarMapper extends EntityMapper<PlanejamentoFamiliar, PlanejamentoFamiliarDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "metodoAtual", source = "metodoAtual", qualifiedByName = "metodoFromCodigo")
    @Mapping(target = "metodoAnterior", source = "metodoAnterior", qualifiedByName = "metodoFromCodigo")
    PlanejamentoFamiliar toEntity(PlanejamentoFamiliarDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "metodoAtual", source = "metodoAtual.codigo")
    @Mapping(target = "metodoAnterior", source = "metodoAnterior.codigo")
    PlanejamentoFamiliarDTO toDTO(PlanejamentoFamiliar entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "metodoAtual", source = "metodoAtual", qualifiedByName = "metodoFromCodigo")
    @Mapping(target = "metodoAnterior", source = "metodoAnterior", qualifiedByName = "metodoFromCodigo")
    PlanejamentoFamiliar fromRequest(PlanejamentoFamiliarRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "metodoAtual", source = "metodoAtual.codigo")
    @Mapping(target = "metodoAtualDescricao", source = "metodoAtual.descricao")
    @Mapping(target = "metodoAnterior", source = "metodoAnterior.codigo")
    @Mapping(target = "metodoAnteriorDescricao", source = "metodoAnterior.descricao")
    PlanejamentoFamiliarResponse toResponse(PlanejamentoFamiliar entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("equipeFromId")
    default EquipeSaude equipeFromId(UUID id) {
        if (id == null) return null;
        EquipeSaude e = new EquipeSaude();
        e.setId(id);
        return e;
    }

    @Named("metodoFromCodigo")
    default TipoMetodoContraceptivoEnum metodoFromCodigo(Integer codigo) {
        return TipoMetodoContraceptivoEnum.fromCodigo(codigo);
    }
}

