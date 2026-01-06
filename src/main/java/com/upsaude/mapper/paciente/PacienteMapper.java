package com.upsaude.mapper.paciente;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.embeddable.InformacoesConvenioPacienteMapper;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.mapper.paciente.deficiencia.DeficienciasPacienteMapper;
import com.upsaude.mapper.sistema.integracao.IntegracaoGovMapper;
import com.upsaude.mapper.sistema.lgpd.LGPDConsentimentoMapper;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

@Mapper(config = MappingConfig.class, uses = {
                ConvenioMapper.class,
                DadosClinicosBasicosMapper.class,
                DadosSociodemograficosMapper.class,
                IntegracaoGovMapper.class,
                LGPDConsentimentoMapper.class,
                DeficienciasPacienteMapper.class,
                InformacoesConvenioPacienteMapper.class,
                PacienteContatoMapper.class,
                PacienteIdentificadorMapper.class,
                PacienteEnderecoMapper.class,
                ResponsavelLegalMapper.class,
                PacienteDadosPessoaisComplementaresMapper.class,
                PacienteObitoMapper.class,
                PacienteVinculoTerritorialMapper.class
})
public interface PacienteMapper {

        InformacoesConvenioPacienteMapper informacoesConvenioMapper = Mappers
                        .getMapper(InformacoesConvenioPacienteMapper.class);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "active", ignore = true)
        @Mapping(target = "convenio", ignore = true)
        @Mapping(target = "integracoesGov", ignore = true)
        @Mapping(target = "enderecos", ignore = true)
        @Mapping(target = "identificadores", ignore = true)
        @Mapping(target = "contatos", ignore = true)
        @Mapping(target = "dadosSociodemograficos", ignore = true)
        @Mapping(target = "dadosClinicosBasicos", ignore = true)
        @Mapping(target = "responsavelLegal", ignore = true)
        @Mapping(target = "dadosPessoaisComplementares", ignore = true)
        @Mapping(target = "obito", ignore = true)
        @Mapping(target = "deficiencias", ignore = true)
        @Mapping(target = "vinculosTerritoriais", ignore = true)
        @Mapping(target = "lgpdConsentimento", ignore = true)
        Paciente fromRequest(PacienteRequest request);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "active", ignore = true)
        @Mapping(target = "convenio", ignore = true)
        @Mapping(target = "integracoesGov", ignore = true)
        @Mapping(target = "enderecos", ignore = true)
        @Mapping(target = "identificadores", ignore = true)
        @Mapping(target = "contatos", ignore = true)
        @Mapping(target = "dadosSociodemograficos", ignore = true)
        @Mapping(target = "dadosClinicosBasicos", ignore = true)
        @Mapping(target = "responsavelLegal", ignore = true)
        @Mapping(target = "dadosPessoaisComplementares", ignore = true)
        @Mapping(target = "obito", ignore = true)
        @Mapping(target = "deficiencias", ignore = true)
        @Mapping(target = "vinculosTerritoriais", ignore = true)
        @Mapping(target = "lgpdConsentimento", ignore = true)
        void updateFromRequest(PacienteRequest request, @MappingTarget Paciente entity);

        @Mapping(target = "enderecos", source = "enderecos")
        @Mapping(target = "identificadores", source = "identificadores")
        @Mapping(target = "contatos", source = "contatos")
        @Mapping(target = "dadosPessoaisComplementares", source = "dadosPessoaisComplementares")
        @Mapping(target = "dadosSociodemograficos", source = "dadosSociodemograficos")
        @Mapping(target = "dadosClinicosBasicos", source = "dadosClinicosBasicos")
        @Mapping(target = "responsavelLegal", source = "responsavelLegal")
        @Mapping(target = "obito", source = "obito")
        @Mapping(target = "deficiencias", source = "deficiencias")
        @Mapping(target = "vinculosTerritoriais", source = "vinculosTerritoriais")
        @Mapping(target = "lgpdConsentimento", source = "lgpdConsentimento")
        @Mapping(target = "integracaoGov", source = "integracoesGov", qualifiedByName = "mapFirstIntegracao")
        PacienteResponse toResponse(Paciente entity);

        @Named("mapFirstIntegracao")
        default IntegracaoGovResponse mapFirstIntegracao(List<IntegracaoGov> list) {
                if (list == null || list.isEmpty()) {
                        return null;
                }
                return Mappers.getMapper(IntegracaoGovMapper.class).toResponseWithoutPatient(list.get(0));
        }

        @Named("toResponseCompleto")
        default PacienteResponse toResponseCompleto(Paciente paciente) {
                return toResponse(paciente);
        }

        default PacienteSimplificadoResponse toSimplified(Paciente paciente) {
                if (paciente == null)
                        return null;

                // Buscar identificadores principais
                String cpf = paciente.getIdentificadores() != null ? paciente.getIdentificadores().stream()
                                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF
                                                && Boolean.TRUE.equals(id.getPrincipal()))
                                .map(id -> id.getValor())
                                .findFirst().orElse(null) : null;

                String cns = paciente.getIdentificadores() != null ? paciente.getIdentificadores().stream()
                                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS
                                                && Boolean.TRUE.equals(id.getPrincipal()))
                                .map(id -> id.getValor())
                                .findFirst().orElse(null) : null;

                String rg = paciente.getIdentificadores() != null ? paciente.getIdentificadores().stream()
                                .filter(id -> id.getTipo() == TipoIdentificadorEnum.RG
                                                && Boolean.TRUE.equals(id.getPrincipal()))
                                .map(id -> id.getValor())
                                .findFirst().orElse(null) : null;

                // Buscar contatos principais
                String telefone = paciente.getContatos() != null ? paciente.getContatos().stream()
                                .filter(c -> c.getTipo() == TipoContatoEnum.TELEFONE)
                                .map(c -> c.getTelefone())
                                .filter(t -> t != null && !t.trim().isEmpty())
                                .findFirst().orElse(null) : null;

                String email = paciente.getContatos() != null ? paciente.getContatos().stream()
                                .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                                .map(c -> c.getEmail())
                                .filter(e -> e != null && !e.trim().isEmpty())
                                .findFirst().orElse(null) : null;

                // Buscar dados pessoais complementares
                String nomeMae = paciente.getDadosPessoaisComplementares() != null
                                ? paciente.getDadosPessoaisComplementares().getNomeMae()
                                : null;
                String nomePai = paciente.getDadosPessoaisComplementares() != null
                                ? paciente.getDadosPessoaisComplementares().getNomePai()
                                : null;
                com.upsaude.enums.IdentidadeGeneroEnum identidadeGenero = paciente
                                .getDadosPessoaisComplementares() != null
                                                ? paciente.getDadosPessoaisComplementares().getIdentidadeGenero()
                                                : null;
                com.upsaude.enums.OrientacaoSexualEnum orientacaoSexual = paciente
                                .getDadosPessoaisComplementares() != null
                                                ? paciente.getDadosPessoaisComplementares().getOrientacaoSexual()
                                                : null;

                // Buscar dados sociodemográficos
                com.upsaude.enums.RacaCorEnum racaCor = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getRacaCor()
                                : null;
                com.upsaude.enums.NacionalidadeEnum nacionalidade = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getNacionalidade()
                                : null;
                String paisNascimento = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getPaisNascimento()
                                : null;
                String naturalidade = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getNaturalidade()
                                : null;
                String municipioNascimentoIbge = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getMunicipioNascimentoIbge()
                                : null;
                com.upsaude.enums.EscolaridadeEnum escolaridade = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getEscolaridade()
                                : null;
                String ocupacaoProfissao = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getOcupacaoProfissao()
                                : null;
                Boolean situacaoRua = paciente.getDadosSociodemograficos() != null
                                ? paciente.getDadosSociodemograficos().getSituacaoRua()
                                : null;

                // Buscar responsável legal
                String responsavelNome = paciente.getResponsavelLegal() != null
                                ? paciente.getResponsavelLegal().getNome()
                                : null;
                String responsavelCpf = paciente.getResponsavelLegal() != null ? paciente.getResponsavelLegal().getCpf()
                                : null;
                String responsavelTelefone = paciente.getResponsavelLegal() != null
                                ? paciente.getResponsavelLegal().getTelefone()
                                : null;

                // Buscar óbito
                LocalDate dataObito = paciente.getObito() != null ? paciente.getObito().getDataObito() : null;
                String causaObitoCid10 = paciente.getObito() != null ? paciente.getObito().getCausaObitoCid10() : null;

                // Buscar integração gov (primeira encontrada)
                Boolean cartaoSusAtivo = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty()
                                ? paciente.getIntegracoesGov().get(0).getCartaoSusAtivo()
                                : null;
                LocalDate dataAtualizacaoCns = paciente.getIntegracoesGov() != null
                                && !paciente.getIntegracoesGov().isEmpty()
                                                ? paciente.getIntegracoesGov().get(0).getDataAtualizacaoCns()
                                                : null;
                String origemCadastro = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty()
                                ? paciente.getIntegracoesGov().get(0).getOrigemCadastro()
                                : null;
                Boolean cnsValidado = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty()
                                ? paciente.getIntegracoesGov().get(0).getCnsValidado()
                                : null;
                com.upsaude.enums.TipoCnsEnum tipoCns = paciente.getIntegracoesGov() != null
                                && !paciente.getIntegracoesGov().isEmpty()
                                                ? paciente.getIntegracoesGov().get(0).getTipoCns()
                                                : null;

                // Buscar vínculo territorial ativo
                Boolean acompanhadoPorEquipeEsf = paciente.getVinculosTerritoriais() != null
                                ? paciente.getVinculosTerritoriais().stream()
                                                .anyMatch(v -> Boolean.TRUE.equals(v.getActive()))
                                : false;

                // Buscar deficiências
                Boolean possuiDeficiencia = paciente.getDeficiencias() != null && !paciente.getDeficiencias().isEmpty();
                String tipoDeficiencia = paciente.getDeficiencias() != null && !paciente.getDeficiencias().isEmpty()
                                ? paciente.getDeficiencias().stream()
                                                .map(d -> d.getDeficiencia() != null ? d.getDeficiencia().getNome()
                                                                : null)
                                                .filter(n -> n != null)
                                                .findFirst().orElse(null)
                                : null;

                return PacienteSimplificadoResponse.builder()
                                .id(paciente.getId())
                                .createdAt(paciente.getCreatedAt())
                                .updatedAt(paciente.getUpdatedAt())
                                .active(paciente.getActive())
                                .nomeCompleto(paciente.getNomeCompleto())
                                .cpf(cpf)
                                .rg(rg)
                                .cns(cns)
                                .dataNascimento(paciente.getDataNascimento())
                                .sexo(paciente.getSexo())
                                .estadoCivil(null) // Removido do core, buscar de dados sociodemográficos se necessário
                                .telefone(telefone)
                                .email(email)
                                .nomeMae(nomeMae)
                                .nomePai(nomePai)
                                .responsavelNome(responsavelNome)
                                .responsavelCpf(responsavelCpf)
                                .responsavelTelefone(responsavelTelefone)
                                .informacoesConvenio(paciente.getInformacoesConvenio() != null
                                                ? informacoesConvenioMapper
                                                                .toResponse(paciente.getInformacoesConvenio())
                                                : null)
                                .observacoes(paciente.getObservacoes())
                                .racaCor(racaCor)
                                .nacionalidade(nacionalidade)
                                .paisNascimento(paisNascimento)
                                .naturalidade(naturalidade)
                                .municipioNascimentoIbge(municipioNascimentoIbge)
                                .escolaridade(escolaridade)
                                .ocupacaoProfissao(ocupacaoProfissao)
                                .situacaoRua(situacaoRua)
                                .statusPaciente(paciente.getStatusPaciente())
                                .dataObito(dataObito)
                                .causaObitoCid10(causaObitoCid10)
                                .cartaoSusAtivo(cartaoSusAtivo)
                                .dataAtualizacaoCns(dataAtualizacaoCns)
                                .tipoAtendimentoPreferencial(paciente.getTipoAtendimentoPreferencial())
                                .origemCadastro(origemCadastro)
                                .nomeSocial(paciente.getNomeSocial())
                                .identidadeGenero(identidadeGenero)
                                .orientacaoSexual(orientacaoSexual)
                                .possuiDeficiencia(possuiDeficiencia)
                                .tipoDeficiencia(tipoDeficiencia)
                                .cnsValidado(cnsValidado)
                                .tipoCns(tipoCns)
                                .acompanhadoPorEquipeEsf(acompanhadoPorEquipeEsf)
                                .build();
        }

        default PacienteSimplificadoResponse fromProjection(PacienteSimplificadoProjection projecao) {
                if (projecao == null)
                        return null;
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
                                .informacoesConvenio(
                                                projecao.getNumeroCarteirinha() != null
                                                                || projecao.getDataValidadeCarteirinha() != null
                                                                                ? com.upsaude.api.response.embeddable.InformacoesConvenioPacienteResponse
                                                                                                .builder()
                                                                                                .numeroCarteirinha(
                                                                                                                projecao.getNumeroCarteirinha())
                                                                                                .dataValidadeCarteirinha(
                                                                                                                projecao.getDataValidadeCarteirinha())
                                                                                                .build()
                                                                                : null)
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
