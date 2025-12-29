package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.embeddable.InformacoesConvenioPacienteMapper;
import com.upsaude.mapper.paciente.deficiencia.DeficienciasPacienteMapper;
import com.upsaude.mapper.sistema.lgpd.LGPDConsentimentoMapper;
import com.upsaude.mapper.sistema.integracao.IntegracaoGovMapper;
import org.mapstruct.factory.Mappers;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.enums.TipoContatoEnum;
import java.time.LocalDate;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, DadosClinicosBasicosMapper.class, IntegracaoGovMapper.class, LGPDConsentimentoMapper.class, DeficienciasPacienteMapper.class, InformacoesConvenioPacienteMapper.class})
public interface PacienteMapper {
    
    InformacoesConvenioPacienteMapper informacoesConvenioMapper = Mappers.getMapper(InformacoesConvenioPacienteMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "dadosClinicosBasicos", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    @Mapping(target = "integracoesGov", ignore = true)
    @Mapping(target = "lgpdConsentimento", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "deficiencias", ignore = true)
    @Mapping(target = "identificadores", ignore = true)
    @Mapping(target = "contatos", ignore = true)
    @Mapping(target = "vinculosTerritoriais", ignore = true)
    @Mapping(target = "dadosPessoaisComplementares", ignore = true)
    @Mapping(target = "obito", ignore = true)
    @Mapping(target = "informacoesConvenio", ignore = true)
    Paciente fromRequest(PacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "dadosClinicosBasicos", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    @Mapping(target = "integracoesGov", ignore = true)
    @Mapping(target = "lgpdConsentimento", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "deficiencias", ignore = true)
    @Mapping(target = "identificadores", ignore = true)
    @Mapping(target = "contatos", ignore = true)
    @Mapping(target = "vinculosTerritoriais", ignore = true)
    @Mapping(target = "dadosPessoaisComplementares", ignore = true)
    @Mapping(target = "obito", ignore = true)
    @Mapping(target = "informacoesConvenio", ignore = true)
    void updateFromRequest(PacienteRequest request, @MappingTarget Paciente entity);

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "rg", ignore = true)
    @Mapping(target = "cns", ignore = true)
    @Mapping(target = "telefone", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "nomeMae", ignore = true)
    @Mapping(target = "nomePai", ignore = true)
    @Mapping(target = "responsavelNome", ignore = true)
    @Mapping(target = "responsavelCpf", ignore = true)
    @Mapping(target = "responsavelTelefone", ignore = true)
    @Mapping(target = "racaCor", ignore = true)
    @Mapping(target = "nacionalidade", ignore = true)
    @Mapping(target = "paisNascimento", ignore = true)
    @Mapping(target = "naturalidade", ignore = true)
    @Mapping(target = "escolaridade", ignore = true)
    @Mapping(target = "ocupacaoProfissao", ignore = true)
    @Mapping(target = "situacaoRua", ignore = true)
    @Mapping(target = "estadoCivil", ignore = true)
    @Mapping(target = "identidadeGenero", ignore = true)
    @Mapping(target = "orientacaoSexual", ignore = true)
    @Mapping(target = "cartaoSusAtivo", ignore = true)
    @Mapping(target = "cnsValidado", ignore = true)
    @Mapping(target = "acompanhadoPorEquipeEsf", ignore = true)
    @Mapping(target = "possuiDeficiencia", ignore = true)
    @Mapping(target = "idade", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    PacienteResponse toResponse(Paciente entity);

    @Named("toResponseCompleto")
    default PacienteResponse toResponseCompleto(Paciente paciente) {
        if (paciente == null) return null;
        
        PacienteResponse response = toResponse(paciente);
        
        // Mapear identificadores
        if (paciente.getIdentificadores() != null) {
            response.setCpf(paciente.getIdentificadores().stream()
                    .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF && Boolean.TRUE.equals(id.getPrincipal()))
                    .map(id -> id.getValor())
                    .findFirst().orElse(null));
            response.setCns(paciente.getIdentificadores().stream()
                    .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS && Boolean.TRUE.equals(id.getPrincipal()))
                    .map(id -> id.getValor())
                    .findFirst().orElse(null));
            response.setRg(paciente.getIdentificadores().stream()
                    .filter(id -> id.getTipo() == TipoIdentificadorEnum.RG && Boolean.TRUE.equals(id.getPrincipal()))
                    .map(id -> id.getValor())
                    .findFirst().orElse(null));
        }
        
        // Mapear contatos
        if (paciente.getContatos() != null) {
            // Buscar telefone
            response.setTelefone(paciente.getContatos().stream()
                    .filter(c -> c.getTipo() == TipoContatoEnum.TELEFONE)
                    .map(c -> c.getTelefone())
                    .filter(t -> t != null && !t.trim().isEmpty())
                    .findFirst().orElse(null));
            
            // Buscar email
            response.setEmail(paciente.getContatos().stream()
                    .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                    .map(c -> c.getEmail())
                    .filter(e -> e != null && !e.trim().isEmpty())
                    .findFirst().orElse(null));
        }
        
        // Mapear dados pessoais complementares
        if (paciente.getDadosPessoaisComplementares() != null) {
            response.setNomeMae(paciente.getDadosPessoaisComplementares().getNomeMae());
            response.setNomePai(paciente.getDadosPessoaisComplementares().getNomePai());
            response.setIdentidadeGenero(paciente.getDadosPessoaisComplementares().getIdentidadeGenero());
            response.setOrientacaoSexual(paciente.getDadosPessoaisComplementares().getOrientacaoSexual());
        }
        
        // Mapear dados sociodemográficos
        if (paciente.getDadosSociodemograficos() != null) {
            response.setRacaCor(paciente.getDadosSociodemograficos().getRacaCor());
            response.setNacionalidade(paciente.getDadosSociodemograficos().getNacionalidade());
            response.setPaisNascimento(paciente.getDadosSociodemograficos().getPaisNascimento());
            response.setNaturalidade(paciente.getDadosSociodemograficos().getNaturalidade());
            response.setEscolaridade(paciente.getDadosSociodemograficos().getEscolaridade());
            response.setOcupacaoProfissao(paciente.getDadosSociodemograficos().getOcupacaoProfissao());
            response.setSituacaoRua(paciente.getDadosSociodemograficos().getSituacaoRua());
        }
        
        // Mapear responsável legal (campos diretos apenas, não o objeto completo para evitar recursão)
        if (paciente.getResponsavelLegal() != null) {
            response.setResponsavelNome(paciente.getResponsavelLegal().getNome());
            response.setResponsavelCpf(paciente.getResponsavelLegal().getCpf());
            response.setResponsavelTelefone(paciente.getResponsavelLegal().getTelefone());
            // Não mapear o objeto completo para evitar recursão circular
        }
        
        // Mapear dados sociodemográficos (campos diretos apenas, não o objeto completo para evitar recursão)
        if (paciente.getDadosSociodemograficos() != null) {
            // Os campos já foram mapeados acima, não precisamos mapear o objeto completo
        }
        
        // Mapear integração gov
        if (paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty()) {
            com.upsaude.entity.sistema.integracao.IntegracaoGov integracao = paciente.getIntegracoesGov().get(0);
            response.setCartaoSusAtivo(integracao.getCartaoSusAtivo());
            response.setCnsValidado(integracao.getCnsValidado());
        }
        
        // Mapear vínculos territoriais
        if (paciente.getVinculosTerritoriais() != null) {
            response.setAcompanhadoPorEquipeEsf(paciente.getVinculosTerritoriais().stream()
                    .anyMatch(v -> Boolean.TRUE.equals(v.getActive())));
        }
        
        // Mapear deficiências
        if (paciente.getDeficiencias() != null) {
            response.setPossuiDeficiencia(!paciente.getDeficiencias().isEmpty());
        }
        
        return response;
    }

    default PacienteSimplificadoResponse toSimplified(Paciente paciente) {
        if (paciente == null) return null;
        
        // Buscar identificadores principais
        String cpf = paciente.getIdentificadores() != null ? 
            paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null) : null;
        
        String cns = paciente.getIdentificadores() != null ? 
            paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null) : null;
        
        String rg = paciente.getIdentificadores() != null ? 
            paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.RG && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null) : null;
        
        // Buscar contatos principais
        String telefone = paciente.getContatos() != null ? 
            paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.TELEFONE)
                .map(c -> c.getTelefone())
                .filter(t -> t != null && !t.trim().isEmpty())
                .findFirst().orElse(null) : null;
        
        String email = paciente.getContatos() != null ? 
            paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                .map(c -> c.getEmail())
                .filter(e -> e != null && !e.trim().isEmpty())
                .findFirst().orElse(null) : null;
        
        // Buscar dados pessoais complementares
        String nomeMae = paciente.getDadosPessoaisComplementares() != null ? 
            paciente.getDadosPessoaisComplementares().getNomeMae() : null;
        String nomePai = paciente.getDadosPessoaisComplementares() != null ? 
            paciente.getDadosPessoaisComplementares().getNomePai() : null;
        com.upsaude.enums.IdentidadeGeneroEnum identidadeGenero = paciente.getDadosPessoaisComplementares() != null ? 
            paciente.getDadosPessoaisComplementares().getIdentidadeGenero() : null;
        com.upsaude.enums.OrientacaoSexualEnum orientacaoSexual = paciente.getDadosPessoaisComplementares() != null ? 
            paciente.getDadosPessoaisComplementares().getOrientacaoSexual() : null;
        
        // Buscar dados sociodemográficos
        com.upsaude.enums.RacaCorEnum racaCor = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getRacaCor() : null;
        com.upsaude.enums.NacionalidadeEnum nacionalidade = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getNacionalidade() : null;
        String paisNascimento = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getPaisNascimento() : null;
        String naturalidade = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getNaturalidade() : null;
        String municipioNascimentoIbge = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getMunicipioNascimentoIbge() : null;
        com.upsaude.enums.EscolaridadeEnum escolaridade = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getEscolaridade() : null;
        String ocupacaoProfissao = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getOcupacaoProfissao() : null;
        Boolean situacaoRua = paciente.getDadosSociodemograficos() != null ? 
            paciente.getDadosSociodemograficos().getSituacaoRua() : null;
        
        // Buscar responsável legal
        String responsavelNome = paciente.getResponsavelLegal() != null ? 
            paciente.getResponsavelLegal().getNome() : null;
        String responsavelCpf = paciente.getResponsavelLegal() != null ? 
            paciente.getResponsavelLegal().getCpf() : null;
        String responsavelTelefone = paciente.getResponsavelLegal() != null ? 
            paciente.getResponsavelLegal().getTelefone() : null;
        
        // Buscar óbito
        LocalDate dataObito = paciente.getObito() != null ? 
            paciente.getObito().getDataObito() : null;
        String causaObitoCid10 = paciente.getObito() != null ? 
            paciente.getObito().getCausaObitoCid10() : null;
        
        // Buscar integração gov (primeira encontrada)
        Boolean cartaoSusAtivo = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty() ? 
            paciente.getIntegracoesGov().get(0).getCartaoSusAtivo() : null;
        LocalDate dataAtualizacaoCns = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty() ? 
            paciente.getIntegracoesGov().get(0).getDataAtualizacaoCns() : null;
        String origemCadastro = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty() ? 
            paciente.getIntegracoesGov().get(0).getOrigemCadastro() : null;
        Boolean cnsValidado = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty() ? 
            paciente.getIntegracoesGov().get(0).getCnsValidado() : null;
        com.upsaude.enums.TipoCnsEnum tipoCns = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty() ? 
            paciente.getIntegracoesGov().get(0).getTipoCns() : null;
        
        // Buscar vínculo territorial ativo
        Boolean acompanhadoPorEquipeEsf = paciente.getVinculosTerritoriais() != null ? 
            paciente.getVinculosTerritoriais().stream()
                .anyMatch(v -> Boolean.TRUE.equals(v.getActive())) : false;
        
        // Buscar deficiências
        Boolean possuiDeficiencia = paciente.getDeficiencias() != null && !paciente.getDeficiencias().isEmpty();
        String tipoDeficiencia = paciente.getDeficiencias() != null && !paciente.getDeficiencias().isEmpty() ? 
            paciente.getDeficiencias().stream()
                .map(d -> d.getDeficiencia() != null ? d.getDeficiencia().getNome() : null)
                .filter(n -> n != null)
                .findFirst().orElse(null) : null;
        
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
                .informacoesConvenio(paciente.getInformacoesConvenio() != null ? 
                    informacoesConvenioMapper.toResponse(paciente.getInformacoesConvenio()) : null)
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
                .informacoesConvenio(projecao.getNumeroCarteirinha() != null || projecao.getDataValidadeCarteirinha() != null ?
                    com.upsaude.api.response.embeddable.InformacoesConvenioPacienteResponse.builder()
                        .numeroCarteirinha(projecao.getNumeroCarteirinha())
                        .dataValidadeCarteirinha(projecao.getDataValidadeCarteirinha())
                        .build() : null)
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
