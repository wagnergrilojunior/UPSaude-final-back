package com.upsaude.mapper;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.dto.CuidadosEnfermagemDTO;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.TipoCuidadoEnfermagemEnum;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre CuidadosEnfermagem entity, DTO, Request e Response.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface CuidadosEnfermagemMapper extends EntityMapper<CuidadosEnfermagem, CuidadosEnfermagemDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "atendimento", source = "atendimentoId", qualifiedByName = "atendimentoFromId")
    @Mapping(target = "tipoCuidado", source = "tipoCuidado", qualifiedByName = "tipoCuidadoFromCodigo")
    CuidadosEnfermagem toEntity(CuidadosEnfermagemDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "atendimentoId", source = "atendimento.id")
    @Mapping(target = "tipoCuidado", source = "tipoCuidado.codigo")
    CuidadosEnfermagemDTO toDTO(CuidadosEnfermagem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "atendimento", source = "atendimentoId", qualifiedByName = "atendimentoFromId")
    @Mapping(target = "tipoCuidado", source = "tipoCuidado", qualifiedByName = "tipoCuidadoFromCodigo")
    CuidadosEnfermagem fromRequest(CuidadosEnfermagemRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "atendimentoId", source = "atendimento.id")
    @Mapping(target = "tipoCuidado", source = "tipoCuidado.codigo")
    @Mapping(target = "tipoCuidadoDescricao", source = "tipoCuidado.descricao")
    CuidadosEnfermagemResponse toResponse(CuidadosEnfermagem entity);

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

    @Named("atendimentoFromId")
    default Atendimento atendimentoFromId(UUID id) {
        if (id == null) return null;
        Atendimento a = new Atendimento();
        a.setId(id);
        return a;
    }

    @Named("tipoCuidadoFromCodigo")
    default TipoCuidadoEnfermagemEnum tipoCuidadoFromCodigo(Integer codigo) {
        return TipoCuidadoEnfermagemEnum.fromCodigo(codigo);
    }
}

