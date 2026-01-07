package com.upsaude.mapper.cnes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.embeddable.ContatoEstabelecimento;
import com.upsaude.entity.embeddable.DadosIdentificacaoEstabelecimento;
import com.upsaude.entity.embeddable.LocalizacaoEstabelecimento;
import com.upsaude.enums.EsferaAdministrativaEnum;
import com.upsaude.integration.cnes.wsdl.cnesservice.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * Mapper para converter dados do CNES (WSDL) para entidades JPA.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CnesEstabelecimentoMapper {

    private final ObjectMapper objectMapper;

    /**
     * Mapeia dados do CNES para a entidade Estabelecimentos.
     * 
     * @param dadosGerais Dados gerais do estabelecimento retornados pelo CNES
     * @param estabelecimento Entidade existente (pode ser nova)
     * @param competencia Competência da sincronização (AAAAMM)
     * @return Estabelecimento atualizado
     */
    public Estabelecimentos mapToEstabelecimento(DadosGeraisEstabelecimentoSaudeType dadosGerais, 
                                                   Estabelecimentos estabelecimento, 
                                                   String competencia) {
        if (dadosGerais == null) {
            log.warn("DadosGeraisEstabelecimentoSaudeType é null, não é possível mapear");
            return estabelecimento;
        }

        // Dados de Identificação
        if (estabelecimento.getDadosIdentificacao() == null) {
            estabelecimento.setDadosIdentificacao(new DadosIdentificacaoEstabelecimento());
        }
        
        DadosIdentificacaoEstabelecimento dadosIdentificacao = estabelecimento.getDadosIdentificacao();
        
        // CNES
        if (dadosGerais.getCodigoCNES() != null && dadosGerais.getCodigoCNES().getCodigo() != null) {
            dadosIdentificacao.setCnes(dadosGerais.getCodigoCNES().getCodigo());
        }
        
        // Nome
        if (dadosGerais.getNomeEmpresarial() != null && dadosGerais.getNomeEmpresarial().getNome() != null) {
            dadosIdentificacao.setNome(dadosGerais.getNomeEmpresarial().getNome());
        } else if (dadosGerais.getNomeFantasia() != null && dadosGerais.getNomeFantasia().getNome() != null) {
            dadosIdentificacao.setNome(dadosGerais.getNomeFantasia().getNome());
        }
        
        // Nome Fantasia
        if (dadosGerais.getNomeFantasia() != null && dadosGerais.getNomeFantasia().getNome() != null) {
            dadosIdentificacao.setNomeFantasia(dadosGerais.getNomeFantasia().getNome());
        }
        
        // CNPJ
        if (dadosGerais.getCNPJ() != null && dadosGerais.getCNPJ().getNumeroCNPJ() != null) {
            dadosIdentificacao.setCnpj(dadosGerais.getCNPJ().getNumeroCNPJ());
        }

        // Esfera Administrativa
        if (dadosGerais.getEsferaAdministrativa() != null && dadosGerais.getEsferaAdministrativa().getCodigo() != null) {
            estabelecimento.setEsferaAdministrativa(mapEsferaAdministrativa(dadosGerais.getEsferaAdministrativa().getCodigo()));
        }

        // Código IBGE Município
        if (dadosGerais.getMunicipioGestor() != null && dadosGerais.getMunicipioGestor().getCodigoMunicipio() != null) {
            estabelecimento.setCodigoIbgeMunicipio(dadosGerais.getMunicipioGestor().getCodigoMunicipio());
        }

        // Contato
        if (estabelecimento.getContato() == null) {
            estabelecimento.setContato(new ContatoEstabelecimento());
        }
        
        ContatoEstabelecimento contato = estabelecimento.getContato();
        
        // Email
        if (dadosGerais.getEmail() != null && dadosGerais.getEmail().getDescricaoEmail() != null) {
            contato.setEmail(dadosGerais.getEmail().getDescricaoEmail());
        }
        
        // Telefones
        if (dadosGerais.getTelefone() != null && !dadosGerais.getTelefone().isEmpty()) {
            TelefoneType primeiroTelefone = dadosGerais.getTelefone().get(0);
            if (primeiroTelefone != null && primeiroTelefone.getNumeroTelefone() != null) {
                String telefoneCompleto = formatarTelefone(primeiroTelefone);
                contato.setTelefone(telefoneCompleto);
            }
            if (dadosGerais.getTelefone().size() > 1) {
                TelefoneType segundoTelefone = dadosGerais.getTelefone().get(1);
                if (segundoTelefone != null && segundoTelefone.getNumeroTelefone() != null) {
                    String celularCompleto = formatarTelefone(segundoTelefone);
                    contato.setCelular(celularCompleto);
                }
            }
        }

        // Localização (coordenadas)
        if (estabelecimento.getLocalizacao() == null) {
            estabelecimento.setLocalizacao(new LocalizacaoEstabelecimento());
        }
        
        LocalizacaoEstabelecimento localizacao = estabelecimento.getLocalizacao();
        
        if (dadosGerais.getLocalizacao() != null) {
            try {
                if (dadosGerais.getLocalizacao().getLatitude() != null) {
                    localizacao.setLatitude(Double.parseDouble(dadosGerais.getLocalizacao().getLatitude()));
                }
                if (dadosGerais.getLocalizacao().getLongitude() != null) {
                    localizacao.setLongitude(Double.parseDouble(dadosGerais.getLocalizacao().getLongitude()));
                }
            } catch (NumberFormatException e) {
                log.warn("Erro ao converter coordenadas do CNES: {}", e.getMessage());
            }
        }

        // Atualizar campos de sincronização
        estabelecimento.setDataUltimaSincronizacaoCnes(OffsetDateTime.now());
        estabelecimento.setVersaoCnes(competencia);

        return estabelecimento;
    }

    /**
     * Converte código de esfera administrativa do CNES para enum.
     */
    private EsferaAdministrativaEnum mapEsferaAdministrativa(String codigo) {
        if (codigo == null) return null;
        
        try {
            Integer codigoInt = Integer.parseInt(codigo);
            return EsferaAdministrativaEnum.fromCodigo(codigoInt);
        } catch (NumberFormatException e) {
            log.warn("Código de esfera administrativa inválido: {}", codigo);
            return null;
        }
    }

    /**
     * Formata telefone do CNES para string.
     */
    private String formatarTelefone(TelefoneType telefone) {
        if (telefone == null) return null;
        
        StringBuilder sb = new StringBuilder();
        if (telefone.getDDD() != null) {
            sb.append("(").append(telefone.getDDD()).append(") ");
        }
        if (telefone.getNumeroTelefone() != null) {
            sb.append(telefone.getNumeroTelefone());
        }
        return sb.length() > 0 ? sb.toString() : null;
    }

    /**
     * Serializa dados do CNES para JSON (para histórico).
     */
    public String serializeToJson(Object dadosCnes) {
        try {
            return objectMapper.writeValueAsString(dadosCnes);
        } catch (Exception e) {
            log.error("Erro ao serializar dados CNES para JSON", e);
            return "{}";
        }
    }
}

