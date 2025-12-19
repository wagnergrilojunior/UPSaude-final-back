package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.paciente.alergia.AlergiasPacienteMapper;
import com.upsaude.mapper.paciente.deficiencia.DeficienciasPacienteMapper;
import com.upsaude.mapper.sistema.LGPDConsentimentoMapper;
import com.upsaude.mapper.sistema.integracao.IntegracaoGovMapper;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, DadosClinicosBasicosMapper.class, DadosSociodemograficosMapper.class, IntegracaoGovMapper.class, LGPDConsentimentoMapper.class, ResponsavelLegalMapper.class, AlergiasPacienteMapper.class, DeficienciasPacienteMapper.class})
public interface PacienteMapper extends EntityMapper<Paciente, PacienteDTO> {

    @Mapping(target = "active", ignore = true)
    Paciente toEntity(PacienteDTO dto);

    PacienteDTO toDTO(Paciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "dadosClinicosBasicos", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    @Mapping(target = "integracaoGov", ignore = true)
    @Mapping(target = "lgpdConsentimento", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "alergias", ignore = true)
    @Mapping(target = "deficiencias", ignore = true)
    Paciente fromRequest(PacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "dadosClinicosBasicos", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    @Mapping(target = "integracaoGov", ignore = true)
    @Mapping(target = "lgpdConsentimento", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "alergias", ignore = true)
    @Mapping(target = "deficiencias", ignore = true)
    void updateFromRequest(PacienteRequest request, @MappingTarget Paciente entity);

    PacienteResponse toResponse(Paciente entity);

    default PacienteSimplificadoResponse toSimplified(Paciente paciente) {
        if (paciente == null) return null;
        return PacienteSimplificadoResponse.builder()
                .id(paciente.getId())
                .createdAt(paciente.getCreatedAt())
                .updatedAt(paciente.getUpdatedAt())
                .active(paciente.getActive())
                .nomeCompleto(paciente.getNomeCompleto())
                .cpf(paciente.getCpf())
                .rg(paciente.getRg())
                .cns(paciente.getCns())
                .dataNascimento(paciente.getDataNascimento())
                .sexo(paciente.getSexo())
                .estadoCivil(paciente.getEstadoCivil())
                .telefone(paciente.getTelefone())
                .email(paciente.getEmail())
                .nomeMae(paciente.getNomeMae())
                .nomePai(paciente.getNomePai())
                .responsavelNome(paciente.getResponsavelNome())
                .responsavelCpf(paciente.getResponsavelCpf())
                .responsavelTelefone(paciente.getResponsavelTelefone())
                .numeroCarteirinha(paciente.getNumeroCarteirinha())
                .dataValidadeCarteirinha(paciente.getDataValidadeCarteirinha())
                .observacoes(paciente.getObservacoes())
                .racaCor(paciente.getRacaCor())
                .nacionalidade(paciente.getNacionalidade())
                .paisNascimento(paciente.getPaisNascimento())
                .naturalidade(paciente.getNaturalidade())
                .municipioNascimentoIbge(paciente.getMunicipioNascimentoIbge())
                .escolaridade(paciente.getEscolaridade())
                .ocupacaoProfissao(paciente.getOcupacaoProfissao())
                .situacaoRua(paciente.getSituacaoRua())
                .statusPaciente(paciente.getStatusPaciente())
                .dataObito(paciente.getDataObito())
                .causaObitoCid10(paciente.getCausaObitoCid10())
                .cartaoSusAtivo(paciente.getCartaoSusAtivo())
                .dataAtualizacaoCns(paciente.getDataAtualizacaoCns())
                .tipoAtendimentoPreferencial(paciente.getTipoAtendimentoPreferencial())
                .origemCadastro(paciente.getOrigemCadastro())
                .nomeSocial(paciente.getNomeSocial())
                .identidadeGenero(paciente.getIdentidadeGenero())
                .orientacaoSexual(paciente.getOrientacaoSexual())
                .possuiDeficiencia(paciente.getPossuiDeficiencia())
                .tipoDeficiencia(paciente.getTipoDeficiencia())
                .cnsValidado(paciente.getCnsValidado())
                .tipoCns(paciente.getTipoCns())
                .acompanhadoPorEquipeEsf(paciente.getAcompanhadoPorEquipeEsf())
                .build();
    }

    default PacienteSimplificadoResponse fromProjection(PacienteSimplificadoProjection projecao) {
        if (projecao == null) return null;
        return PacienteSimplificadoResponse.builder()
                .id(projecao.getId())
                .createdAt(projecao.getCreatedAt())
                .updatedAt(projecao.getUpdatedAt())
                .active(projecao.getActive())
                .nomeCompleto(projecao.getNomeCompleto())
                .cpf(projecao.getCpf())
                .rg(projecao.getRg())
                .cns(projecao.getCns())
                .dataNascimento(projecao.getDataNascimento())
                .sexo(projecao.getSexo())
                .estadoCivil(projecao.getEstadoCivil())
                .telefone(projecao.getTelefone())
                .email(projecao.getEmail())
                .nomeMae(projecao.getNomeMae())
                .nomePai(projecao.getNomePai())
                .responsavelNome(projecao.getResponsavelNome())
                .responsavelCpf(projecao.getResponsavelCpf())
                .responsavelTelefone(projecao.getResponsavelTelefone())
                .numeroCarteirinha(projecao.getNumeroCarteirinha())
                .dataValidadeCarteirinha(projecao.getDataValidadeCarteirinha())
                .observacoes(projecao.getObservacoes())
                .racaCor(projecao.getRacaCor())
                .nacionalidade(projecao.getNacionalidade())
                .paisNascimento(projecao.getPaisNascimento())
                .naturalidade(projecao.getNaturalidade())
                .municipioNascimentoIbge(projecao.getMunicipioNascimentoIbge())
                .escolaridade(projecao.getEscolaridade())
                .ocupacaoProfissao(projecao.getOcupacaoProfissao())
                .situacaoRua(projecao.getSituacaoRua())
                .statusPaciente(projecao.getStatusPaciente())
                .dataObito(projecao.getDataObito())
                .causaObitoCid10(projecao.getCausaObitoCid10())
                .cartaoSusAtivo(projecao.getCartaoSusAtivo())
                .dataAtualizacaoCns(projecao.getDataAtualizacaoCns())
                .tipoAtendimentoPreferencial(projecao.getTipoAtendimentoPreferencial())
                .origemCadastro(projecao.getOrigemCadastro())
                .nomeSocial(projecao.getNomeSocial())
                .identidadeGenero(projecao.getIdentidadeGenero())
                .orientacaoSexual(projecao.getOrientacaoSexual())
                .possuiDeficiencia(projecao.getPossuiDeficiencia())
                .tipoDeficiencia(projecao.getTipoDeficiencia())
                .cnsValidado(projecao.getCnsValidado())
                .tipoCns(projecao.getTipoCns())
                .acompanhadoPorEquipeEsf(projecao.getAcompanhadoPorEquipeEsf())
                .build();
    }
}
