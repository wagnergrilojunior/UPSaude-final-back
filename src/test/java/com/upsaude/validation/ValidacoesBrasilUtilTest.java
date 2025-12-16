package com.upsaude.validation;

import com.upsaude.validation.util.CepUtil;
import com.upsaude.validation.util.DocumentoUtil;
import com.upsaude.validation.util.TelefoneUtil;
import com.upsaude.validation.util.UrlUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacoesBrasilUtilTest {

    @Test
    void deveValidarCpfComOuSemMascara() {
        assertTrue(DocumentoUtil.isCpfValido("529.982.247-25"));
        assertTrue(DocumentoUtil.isCpfValido("52998224725"));
        assertFalse(DocumentoUtil.isCpfValido("123"));
        assertFalse(DocumentoUtil.isCpfValido("111.111.111-11"));
    }

    @Test
    void deveValidarCnpjComOuSemMascara() {
        assertTrue(DocumentoUtil.isCnpjValido("04.252.011/0001-10"));
        assertTrue(DocumentoUtil.isCnpjValido("04252011000110"));
        assertFalse(DocumentoUtil.isCnpjValido("123"));
        assertFalse(DocumentoUtil.isCnpjValido("00000000000000"));
    }

    @Test
    void deveValidarCnsDerivadoDePisEPorModulo11() {
        String pis = "12345678901"; // começa com 1
        String cns = DocumentoUtil.gerarCnsAPartirPis(pis);
        assertNotNull(cns);
        assertEquals(15, cns.length());
        assertTrue(DocumentoUtil.isCnsValido(cns));
        assertTrue(DocumentoUtil.isCnsValido(formatarComMascaraQualquer(cns)));

        // inválido alterando último dígito
        String cnsInvalido = cns.substring(0, 14) + ((cns.charAt(14) != '0') ? "0" : "1");
        assertFalse(DocumentoUtil.isCnsValido(cnsInvalido));

        // CNS gerado (7/8/9): achar um válido por brute-force
        String cnsGerado = gerarCnsGeradoValido('7');
        assertTrue(DocumentoUtil.isCnsValido(cnsGerado));
        assertFalse(DocumentoUtil.isCnsValido("799")); // curto
    }

    @Test
    void deveValidarCnes() {
        assertTrue(DocumentoUtil.isCnesValido("1234567"));
        assertTrue(DocumentoUtil.isCnesValido("123.456-7"));
        assertFalse(DocumentoUtil.isCnesValido("123"));
    }

    @Test
    void deveValidarCep() {
        assertTrue(CepUtil.isCepValido("01001000"));
        assertTrue(CepUtil.isCepValido("01001-000"));
        assertFalse(CepUtil.isCepValido("123"));
    }

    @Test
    void deveValidarTelefoneEcelularComDdd() {
        assertTrue(TelefoneUtil.isTelefoneFixoValido("11 3234-5678"));
        assertTrue(TelefoneUtil.isCelularValido("(11) 98765-4321"));
        assertTrue(TelefoneUtil.isTelefoneOuCelularValido("11987654321"));
        assertTrue(TelefoneUtil.isTelefoneOuCelularValido("1132345678"));

        assertFalse(TelefoneUtil.isCelularValido("1132345678")); // fixo não é celular
        assertFalse(TelefoneUtil.isTelefoneOuCelularValido("00987654321")); // ddd inválido
    }

    @Test
    void deveValidarSiteComHttpOuHttps() {
        assertTrue(UrlUtil.isSiteValido("https://example.com"));
        assertTrue(UrlUtil.isSiteValido("http://example.com/path"));
        assertFalse(UrlUtil.isSiteValido("ftp://example.com"));
        assertFalse(UrlUtil.isSiteValido("www.google.com"));
    }

    private static String formatarComMascaraQualquer(String digits) {
        // Só para garantir que "com máscara" (não dígitos) é aceito pela normalização
        return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6, 9) + "-" + digits.substring(9);
    }

    private static String gerarCnsGeradoValido(char primeiro) {
        // Gera 15 dígitos iniciando com 7/8/9 tal que soma ponderada (15..1) % 11 == 0
        // Estratégia: fixa 14 primeiros dígitos e brute-force o último.
        String base14 = primeiro + "1234567890123"; // 14 dígitos (1 + 13 = 14)
        for (int ultimo = 0; ultimo <= 9; ultimo++) {
            String candidato = base14 + ultimo;
            if (DocumentoUtil.isCnsValido(candidato)) {
                return candidato;
            }
        }
        fail("Não foi possível gerar CNS válido para teste");
        return null;
    }
}
