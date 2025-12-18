package com.upsaude.mapper.clinica.exame;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.exame.ExamesRequest;
import com.upsaude.api.response.clinica.exame.ExamesResponse;
import com.upsaude.dto.clinica.exame.ExamesDTO;
import com.upsaude.entity.clinica.exame.Exames;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.clinica.atendimento.ConsultasMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

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
