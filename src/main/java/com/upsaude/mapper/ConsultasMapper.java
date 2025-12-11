package com.upsaude.mapper;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.dto.ConsultasDTO;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, ConvenioMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, com.upsaude.mapper.embeddable.InformacoesConsultaMapper.class, com.upsaude.mapper.embeddable.AnamneseConsultaMapper.class, com.upsaude.mapper.embeddable.DiagnosticoConsultaMapper.class, com.upsaude.mapper.embeddable.PrescricaoConsultaMapper.class, com.upsaude.mapper.embeddable.ExamesSolicitadosConsultaMapper.class, com.upsaude.mapper.embeddable.EncaminhamentoConsultaMapper.class, com.upsaude.mapper.embeddable.AtestadoConsultaMapper.class})
public interface ConsultasMapper extends EntityMapper<Consultas, ConsultasDTO> {

    @Mapping(target = "active", ignore = true)
    Consultas toEntity(ConsultasDTO dto);

    ConsultasDTO toDTO(Consultas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    Consultas fromRequest(ConsultasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    void updateFromRequest(ConsultasRequest request, @MappingTarget Consultas entity);

    ConsultasResponse toResponse(Consultas entity);
}
