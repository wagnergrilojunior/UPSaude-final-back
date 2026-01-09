package com.upsaude.mapper.paciente;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.embeddable.ContatoPacienteMapper;
import com.upsaude.mapper.embeddable.DadosDemograficosPacienteMapper;
import com.upsaude.mapper.embeddable.DadosPessoaisBasicosPacienteMapper;
import com.upsaude.mapper.embeddable.DocumentosBasicosPacienteMapper;
import com.upsaude.mapper.embeddable.InformacoesConvenioPacienteMapper;
import com.upsaude.mapper.embeddable.IntegracaoGovPacienteMapper;
import com.upsaude.mapper.embeddable.ResponsavelLegalPacienteMapper;
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
                PacienteVinculoTerritorialMapper.class,
                EnderecoMapper.class
})
public abstract class PacienteMapper {

        @Autowired
        protected PacienteEnderecoMapper pacienteEnderecoMapper;

        @Autowired
        protected EnderecoMapper enderecoMapper;

        @Autowired
        protected InformacoesConvenioPacienteMapper informacoesConvenioMapper;

        @Autowired
        protected DadosPessoaisBasicosPacienteMapper dadosPessoaisBasicosPacienteMapper;

        @Autowired
        protected DocumentosBasicosPacienteMapper documentosBasicosPacienteMapper;

        @Autowired
        protected DadosDemograficosPacienteMapper dadosDemograficosPacienteMapper;

        @Autowired
        protected ContatoPacienteMapper contatoPacienteMapper;

        @Autowired
        protected IntegracaoGovPacienteMapper integracaoGovPacienteMapper;

        @Autowired
        protected ResponsavelLegalPacienteMapper responsavelLegalPacienteMapper;

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "active", ignore = true)
        @Mapping(target = "nomeCompleto", source = "dadosPessoaisBasicos.nomeCompleto")
        @Mapping(target = "nomeSocial", source = "dadosPessoaisBasicos.nomeSocial")
        @Mapping(target = "dataNascimento", source = "dadosPessoaisBasicos.dataNascimento")
        @Mapping(target = "sexo", source = "dadosPessoaisBasicos.sexo")
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
        public abstract Paciente fromRequest(PacienteRequest request);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "active", ignore = true)
        @Mapping(target = "nomeCompleto", source = "dadosPessoaisBasicos.nomeCompleto")
        @Mapping(target = "nomeSocial", source = "dadosPessoaisBasicos.nomeSocial")
        @Mapping(target = "dataNascimento", source = "dadosPessoaisBasicos.dataNascimento")
        @Mapping(target = "sexo", source = "dadosPessoaisBasicos.sexo")
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
        public abstract void updateFromRequest(PacienteRequest request, @MappingTarget Paciente entity);

        @Mapping(target = "enderecos", source = "enderecos", qualifiedByName = "mapEnderecos")
        @Mapping(target = "dadosPessoaisComplementares", source = "dadosPessoaisComplementares")
        @Mapping(target = "dadosClinicosBasicos", source = "dadosClinicosBasicos")
        @Mapping(target = "obito", source = "obito")
        @Mapping(target = "deficiencias", source = "deficiencias")
        @Mapping(target = "vinculosTerritoriais", source = "vinculosTerritoriais")
        @Mapping(target = "lgpdConsentimento", source = "lgpdConsentimento")
        @Mapping(target = "dadosPessoaisBasicos", source = ".", qualifiedByName = "mapDadosPessoaisBasicos")
        @Mapping(target = "documentosBasicos", source = ".", qualifiedByName = "mapDocumentosBasicos")
        @Mapping(target = "dadosDemograficos", source = ".", qualifiedByName = "mapDadosDemograficos")
        @Mapping(target = "contato", source = ".", qualifiedByName = "mapContato")
        @Mapping(target = "integracaoGov", source = ".", qualifiedByName = "mapIntegracaoGov")
        @Mapping(target = "responsavelLegal", source = ".", qualifiedByName = "mapResponsavelLegal")
        public abstract PacienteResponse toResponse(Paciente entity);

        @Named("mapDadosPessoaisBasicos")
        public com.upsaude.api.response.embeddable.DadosPessoaisBasicosPacienteResponse mapDadosPessoaisBasicos(
                        Paciente paciente) {
                return dadosPessoaisBasicosPacienteMapper.toResponse(paciente);
        }

        @Named("mapDocumentosBasicos")
        public com.upsaude.api.response.embeddable.DocumentosBasicosPacienteResponse mapDocumentosBasicos(
                        Paciente paciente) {
                return documentosBasicosPacienteMapper.toResponse(paciente);
        }

        @Named("mapDadosDemograficos")
        public com.upsaude.api.response.embeddable.DadosDemograficosPacienteResponse mapDadosDemograficos(
                        Paciente paciente) {
                return dadosDemograficosPacienteMapper.toResponse(paciente);
        }

        @Named("mapContato")
        public com.upsaude.api.response.embeddable.ContatoPacienteResponse mapContato(Paciente paciente) {
                return contatoPacienteMapper.toResponse(paciente);
        }

        @Named("mapIntegracaoGov")
        public com.upsaude.api.response.embeddable.IntegracaoGovPacienteResponse mapIntegracaoGov(Paciente paciente) {
                return integracaoGovPacienteMapper.toResponse(paciente);
        }

        @Named("mapResponsavelLegal")
        public com.upsaude.api.response.embeddable.ResponsavelLegalPacienteResponse mapResponsavelLegal(
                        Paciente paciente) {
                return responsavelLegalPacienteMapper.toResponse(paciente);
        }

        @Named("mapEnderecos")
        public List<com.upsaude.api.response.geral.EnderecoResponse> mapEnderecos(
                        List<com.upsaude.entity.paciente.PacienteEndereco> enderecos) {
                if (enderecos == null) {
                        return new java.util.ArrayList<>();
                }
                return enderecos.stream()
                                .map(pe -> pe.getEndereco() != null
                                                ? pacienteEnderecoMapper.toResponse(pe)
                                                : null)
                                .filter(e -> e != null)
                                .collect(java.util.stream.Collectors.toList());
        }

        @Named("mapEnderecosSimplificado")
        public List<com.upsaude.api.response.geral.EnderecoResponse> mapEnderecosSimplificado(
                        List<com.upsaude.entity.paciente.PacienteEndereco> enderecos) {
                if (enderecos == null) {
                        return new java.util.ArrayList<>();
                }
                return enderecos.stream()
                                .map(pe -> pe.getEndereco() != null
                                                ? pacienteEnderecoMapper.toResponse(pe) // Aqui deveria ser simplificado
                                                                                        // se existisse, mas usaremos o
                                                                                        // principal por agora que está
                                                                                        // injetado corretamente
                                                : null)
                                .filter(e -> e != null)
                                .collect(java.util.stream.Collectors.toList());
        }

        @Named("toResponseCompleto")
        public PacienteResponse toResponseCompleto(Paciente paciente) {
                return toResponse(paciente);
        }

        @Named("toResponseSimplificado")
        public PacienteResponse toResponseSimplificado(Paciente paciente) {
                if (paciente == null) {
                        return null;
                }
                PacienteResponse response = toResponse(paciente);
                if (response != null && paciente.getEnderecos() != null) {
                        response.setEnderecos(mapEnderecosSimplificado(paciente.getEnderecos()));
                }
                return response;
        }

        public PacienteSimplificadoResponse toSimplified(Paciente paciente) {
                if (paciente == null)
                        return null;

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

                String responsavelNome = paciente.getResponsavelLegal() != null
                                ? paciente.getResponsavelLegal().getNome()
                                : null;
                String responsavelCpf = paciente.getResponsavelLegal() != null ? paciente.getResponsavelLegal().getCpf()
                                : null;
                String responsavelTelefone = paciente.getResponsavelLegal() != null
                                ? paciente.getResponsavelLegal().getTelefone()
                                : null;

                LocalDate dataObito = paciente.getObito() != null ? paciente.getObito().getDataObito() : null;
                String causaObitoCid10 = paciente.getObito() != null ? paciente.getObito().getCausaObitoCid10() : null;

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

                Boolean acompanhadoPorEquipeEsf = paciente.getVinculosTerritoriais() != null
                                ? paciente.getVinculosTerritoriais().stream()
                                                .anyMatch(v -> Boolean.TRUE.equals(v.getActive()))
                                : false;

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
                                .estadoCivil(null)
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

        public PacienteSimplificadoResponse fromProjection(PacienteSimplificadoProjection projecao) {
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
