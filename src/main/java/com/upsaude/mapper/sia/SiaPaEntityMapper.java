package com.upsaude.mapper.sia;

import com.upsaude.entity.referencia.sia.SiaPa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Component
public class SiaPaEntityMapper {

    public SiaPa mapToSiaPa(Map<String, String> fields, String competencia, String uf) {
        SiaPa siaPa = new SiaPa();

        siaPa.setCompetencia(competencia != null ? competencia : limparString(fields.get("PA_CMP")));
        siaPa.setUf(uf);
        siaPa.setMesMovimentacao(limparString(fields.get("PA_MVM")));

        siaPa.setCodigoCnes(limparString(fields.get("PA_CODUNI")));
        siaPa.setMunicipioUfmunCodigo(limparString(fields.get("PA_UFMUN")));
        siaPa.setMunicipioGestaoCodigo(limparString(fields.get("PA_GESTAO")));
        siaPa.setCondicaoEstabelecimento(limparString(fields.get("PA_CONDIC")));
        siaPa.setRegiaoControle(limparString(fields.get("PA_REGCT")));
        siaPa.setInconsistenciaSaida(limparString(fields.get("PA_INCOUT")));
        siaPa.setInconsistenciaUrgencia(limparString(fields.get("PA_INCURG")));
        siaPa.setTipoUnidade(limparString(fields.get("PA_TPUPS")));
        siaPa.setTipoPrestador(limparString(fields.get("PA_TIPPRE")));
        siaPa.setIndicadorManual(limparString(fields.get("PA_MN_IND")));
        siaPa.setCnpjCpf(limparString(fields.get("PA_CNPJCPF")));
        siaPa.setCnpjMantenedor(limparString(fields.get("PA_CNPJMNT")));
        siaPa.setCnpjContratante(limparString(fields.get("PA_CNPJ_CC")));
        siaPa.setNaturezaJuridica(limparString(fields.get("PA_NAT_JUR")));
        siaPa.setCodigoOrgaoContratante(limparString(fields.get("PA_CODOCO")));
        siaPa.setServicoContratualizado(limparString(fields.get("PA_SRV_C")));
        siaPa.setCodigoIne(limparString(fields.get("PA_INE")));

        siaPa.setProcedimentoCodigo(limparString(fields.get("PA_PROC_ID")));
        siaPa.setNivelComplexidade(limparString(fields.get("PA_NIVCPL")));
        siaPa.setIndicador(limparString(fields.get("PA_INDICA")));

        siaPa.setTipoFinanciamento(limparString(fields.get("PA_TPFIN")));
        siaPa.setSubfinanciamento(limparString(fields.get("PA_SUBFIN")));

        siaPa.setDocumentoOrigem(limparString(fields.get("PA_DOCORIG")));
        siaPa.setNumeroAutorizacao(limparString(fields.get("PA_AUTORIZ")));

        siaPa.setCnsProfissional(limparString(fields.get("PA_CNSMED")));
        siaPa.setCboCodigo(limparString(fields.get("PA_CBOCOD")));

        siaPa.setCidPrincipalCodigo(limparString(fields.get("PA_CIDPRI")));
        siaPa.setCidSecundarioCodigo(limparString(fields.get("PA_CIDSEC")));
        siaPa.setCidCausaCodigo(limparString(fields.get("PA_CIDCAS")));

        siaPa.setCaraterAtendimento(limparString(fields.get("PA_CATEND")));

        siaPa.setIdade(parseInteger(fields.get("PA_IDADE")));
        siaPa.setIdadeMinima(parseInteger(fields.get("IDADEMIN")));
        siaPa.setIdadeMaxima(parseInteger(fields.get("IDADEMAX")));
        siaPa.setFlagIdade(limparString(fields.get("PA_FLIDADE")));
        siaPa.setSexo(limparString(fields.get("PA_SEXO")));
        siaPa.setRacaCor(limparString(fields.get("PA_RACACOR")));
        siaPa.setEtnia(limparString(fields.get("PA_ETNIA")));
        siaPa.setMunicipioPacienteCodigo(limparString(fields.get("PA_MUNPCN")));

        siaPa.setMotivoSaida(limparString(fields.get("PA_MOTSAI")));
        siaPa.setFlagObito(limparString(fields.get("PA_OBITO")));
        siaPa.setFlagEncerramento(limparString(fields.get("PA_ENCERR")));
        siaPa.setFlagPermanencia(limparString(fields.get("PA_PERMAN")));
        siaPa.setFlagAlta(limparString(fields.get("PA_ALTA")));
        siaPa.setFlagTransferencia(limparString(fields.get("PA_TRANSF")));

        siaPa.setQuantidadeProduzida(parseInteger(fields.get("PA_QTDPRO")));
        siaPa.setQuantidadeAprovada(parseInteger(fields.get("PA_QTDAPR")));
        siaPa.setFlagQuantidade(limparString(fields.get("PA_FLQT")));
        siaPa.setFlagErro(limparString(fields.get("PA_FLER")));

        siaPa.setValorProduzido(parseBigDecimal(fields.get("PA_VALPRO")));
        siaPa.setValorAprovado(parseBigDecimal(fields.get("PA_VALAPR")));
        siaPa.setValorCofinanciado(parseBigDecimal(fields.get("PA_VL_CF")));
        siaPa.setValorClinico(parseBigDecimal(fields.get("PA_VL_CL")));
        siaPa.setValorIncrementado(parseBigDecimal(fields.get("PA_VL_INC")));
        siaPa.setValorTotalVpa(parseBigDecimal(fields.get("NU_VPA_TOT")));
        siaPa.setTotalPa(parseBigDecimal(fields.get("NU_PA_TOT")));

        siaPa.setUfDiferente(limparString(fields.get("PA_UFDIF")));
        siaPa.setMunicipioDiferente(limparString(fields.get("PA_MNDIF")));
        siaPa.setDiferencaValor(parseBigDecimal(fields.get("PA_DIF_VAL")));

        return siaPa;
    }

    private String limparString(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }

    private Integer parseInteger(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            String valorLimpo = valor.trim();
            return Integer.parseInt(valorLimpo);
        } catch (NumberFormatException e) {
            log.debug("Erro ao converter para Integer: {}", valor);
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            String valorLimpo = valor.trim();
            BigDecimal bd = new BigDecimal(valorLimpo);

            if (bd.abs().compareTo(new BigDecimal("999999999999")) > 0) {
                log.debug("Valor BigDecimal suspeito (muito grande): {}", valor);
                return null;
            }

            return bd;
        } catch (NumberFormatException e) {
            log.debug("Erro ao converter para BigDecimal: {}", valor);
            return null;
        } catch (ArithmeticException e) {
            log.debug("Erro aritmético ao converter BigDecimal: {}", valor);
            return null;
        }
    }
}
