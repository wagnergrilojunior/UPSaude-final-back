package com.upsaude.mapper.clinica.atendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultaResponse;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.embeddable.InformacoesConsultaMapper;
import com.upsaude.mapper.embeddable.AnamneseConsultaMapper;
import com.upsaude.mapper.embeddable.DiagnosticoConsultaMapper;
import com.upsaude.mapper.embeddable.PrescricaoConsultaMapper;
import com.upsaude.mapper.embeddable.ExamesSolicitadosConsultaMapper;
import com.upsaude.mapper.embeddable.EncaminhamentoConsultaMapper;
import com.upsaude.mapper.embeddable.AtestadoConsultaMapper;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, InformacoesConsultaMapper.class, AnamneseConsultaMapper.class, DiagnosticoConsultaMapper.class, PrescricaoConsultaMapper.class, ExamesSolicitadosConsultaMapper.class, EncaminhamentoConsultaMapper.class, AtestadoConsultaMapper.class})
public interface ConsultaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    Consulta fromRequest(ConsultaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    void updateFromRequest(ConsultaRequest request, @MappingTarget Consulta entity);

    @Mapping(target = "medico", ignore = true)
    ConsultaResponse toResponse(Consulta entity);
}

