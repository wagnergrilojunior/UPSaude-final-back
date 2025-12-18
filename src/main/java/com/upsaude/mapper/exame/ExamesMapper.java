package com.upsaude.mapper.exame;

import com.upsaude.api.request.exame.ExamesRequest;
import com.upsaude.api.response.exame.ExamesResponse;
import com.upsaude.dto.ExamesDTO;
import com.upsaude.entity.exame.Exames;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.exame.CatalogoExames;
import com.upsaude.entity.atendimento.Consultas;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, CatalogoExamesMapper.class, ConsultasMapper.class, EstabelecimentosMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface ExamesMapper extends EntityMapper<Exames, ExamesDTO> {

    @Mapping(target = "active", ignore = true)
    Exames toEntity(ExamesDTO dto);

    ExamesDTO toDTO(Exames entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "catalogoExame", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "estabelecimentoRealizador", ignore = true)
    @Mapping(target = "medicoResponsavel", ignore = true)
    @Mapping(target = "medicoSolicitante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionalSolicitante", ignore = true)
    Exames fromRequest(ExamesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "catalogoExame", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "estabelecimentoRealizador", ignore = true)
    @Mapping(target = "medicoResponsavel", ignore = true)
    @Mapping(target = "medicoSolicitante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionalSolicitante", ignore = true)
    void updateFromRequest(ExamesRequest request, @MappingTarget Exames entity);

    ExamesResponse toResponse(Exames entity);
}
