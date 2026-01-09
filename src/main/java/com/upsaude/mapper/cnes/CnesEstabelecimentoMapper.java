package com.upsaude.mapper.cnes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.embeddable.ContatoEstabelecimento;
import com.upsaude.entity.embeddable.DadosIdentificacaoEstabelecimento;
import com.upsaude.entity.embeddable.LicenciamentoEstabelecimento;
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
     * @param dadosGerais     Dados gerais do estabelecimento retornados pelo CNES
     * @param estabelecimento Entidade existente (pode ser nova)
     * @param competencia     Competência da sincronização (AAAAMM)
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

        // Tipo de Estabelecimento (obrigatório)
        if (dadosGerais.getTipoUnidade() != null && dadosGerais.getTipoUnidade().getCodigo() != null) {
            dadosIdentificacao.setTipo(mapTipoEstabelecimento(dadosGerais.getTipoUnidade().getCodigo()));
        } else {
            // Fallback para OUTRO se não vier do CNES
            dadosIdentificacao.setTipo(com.upsaude.enums.TipoEstabelecimentoEnum.OUTRO);
        }

        // Esfera Administrativa
        if (dadosGerais.getEsferaAdministrativa() != null
                && dadosGerais.getEsferaAdministrativa().getCodigo() != null) {
            estabelecimento.setEsferaAdministrativa(
                    mapEsferaAdministrativa(dadosGerais.getEsferaAdministrativa().getCodigo()));
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
     * Mapeia dados complementares para o estabelecimento.
     */
    public void mapDadosComplementares(DadosComplementaresType dadosComplementares, Estabelecimentos estabelecimento) {
        if (dadosComplementares == null)
            return;

        // CNAE
        if (dadosComplementares.getCnaePrincipal() != null) {
            estabelecimento.getDadosIdentificacao()
                    .setCnaePrincipal(dadosComplementares.getCnaePrincipal().getCodigo());
        }
        if (dadosComplementares.getCnaeSecundario() != null) {
            estabelecimento.getDadosIdentificacao()
                    .setCnaeSecundario(dadosComplementares.getCnaeSecundario().getCodigo());
        }

        // Alvará / Licença
        if (estabelecimento.getLicenciamento() == null) {
            estabelecimento.setLicenciamento(new LicenciamentoEstabelecimento());
        }

        if (dadosComplementares.getAlvaraSanitario() != null) {
            AlvaraSanitarioType alvara = dadosComplementares.getAlvaraSanitario();
            if (alvara.getNumeroAlvara() != null) {
                estabelecimento.getLicenciamento().setNumeroAlvara(alvara.getNumeroAlvara());
            }
            // Mapeia data de validade se disponível
            if (alvara.getDataVigenciaFinal() != null) {
                // Converter XMLGregorianCalendar para OffsetDateTime se necessário
                // Para simplificar agora, vamos apenas focar no número se houver outro campo
            }
        }
    }

    /**
     * Converte código de tipo de estabelecimento do CNES para enum.
     * Códigos comuns do CNES:
     * - 01-07: Hospitais
     * - 15: Unidade Mista
     * - 20: Posto de Saúde
     * - 22: Consultório Isolado
     * - 32: Unidade Móvel Fluvial
     * - 36: Clínica/Centro de Especialidade
     * - 39: Unidade de Apoio Diagnose e Terapia (SADT Isolado)
     * - 40: Unidade Móvel Terrestre
     * - 42: Unidade Móvel de Nível Pré-Hospitalar na Área de Urgência
     * - 50: Cooperativa
     * - 60: Farmácia
     * - 61: Unidade de Vigilância em Saúde
     * - 62: Laboratório Central de Saúde Pública - LACEN
     * - 67: Secretaria de Saúde
     * - 68: Central de Gestão em Saúde
     * - 69: Centro de Atenção Hemoterapia e ou Hematológica
     * - 70: Centro de Atenção Psicossocial
     * - 71: Centro de Apoio a Saúde da Família
     * - 72: Unidade de Atenção à Saúde Indígena
     * - 73: Pronto Socorro Geral
     * - 74: Pronto Socorro Especializado
     * - 75: Polo Academia da Saúde
     * - 76: Telessaúde
     * - 77: Central de Regulação de Serviços de Saúde
     * - 78: Central de Notificação, Captação e Distribuição de Órgãos Estadual
     * - 79: Central de Regulação Médica das Urgências
     * - 80: Polo de Prevenção de Doenças e Agravos e Promoção da Saúde
     * - 81: Central de Abastecimento
     * - 82: Unidade de Atenção em Regime Residencial
     * - 83: Polo de Prevenção de Doenças e Agravos e Promoção da Saúde
     */
    private com.upsaude.enums.TipoEstabelecimentoEnum mapTipoEstabelecimento(String codigo) {
        if (codigo == null) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.OUTRO;
        }

        String codigoTrimmed = codigo.trim();

        // Hospitais (códigos 01-07, 15)
        if (codigoTrimmed.matches("0[1-7]") || codigoTrimmed.equals("15")) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.HOSPITAL;
        }

        // Postos de Saúde e UBS (códigos 20, 71, 72)
        if (codigoTrimmed.equals("20") || codigoTrimmed.equals("71") || codigoTrimmed.equals("72")) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.POSTO_SAUDE;
        }

        // UPA e Pronto Socorro (códigos 42, 73, 74)
        if (codigoTrimmed.equals("42") || codigoTrimmed.equals("73") || codigoTrimmed.equals("74")) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.UPA;
        }

        // Clínicas e Consultórios (códigos 22, 36)
        if (codigoTrimmed.equals("22") || codigoTrimmed.equals("36")) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.CLINICA;
        }

        // Laboratórios (códigos 39, 62)
        if (codigoTrimmed.equals("39") || codigoTrimmed.equals("62")) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.LABORATORIO;
        }

        // Farmácia (código 60)
        if (codigoTrimmed.equals("60")) {
            return com.upsaude.enums.TipoEstabelecimentoEnum.FARMACIA;
        }

        // Outros tipos não mapeados
        return com.upsaude.enums.TipoEstabelecimentoEnum.OUTRO;
    }

    /**
     * Converte código de esfera administrativa do CNES para enum.
     */
    private EsferaAdministrativaEnum mapEsferaAdministrativa(String codigo) {
        if (codigo == null)
            return null;

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
        if (telefone == null)
            return null;

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
