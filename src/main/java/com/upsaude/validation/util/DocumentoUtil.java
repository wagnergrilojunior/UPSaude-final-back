package com.upsaude.validation.util;

public final class DocumentoUtil {

    private DocumentoUtil() {}

    public static String somenteDigitos(CharSequence value) {
        if (value == null) return null;
        String s = value.toString().trim();
        if (s.isEmpty()) return "";
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static boolean isBlank(CharSequence value) {
        if (value == null) return true;
        return value.toString().trim().isEmpty();
    }

    public static boolean somenteNumerosEComprimento(String digits, int length) {
        if (digits == null) return false;
        if (digits.length() != length) return false;
        for (int i = 0; i < digits.length(); i++) {
            char c = digits.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public static boolean todosOsDigitosIguais(String digits) {
        if (digits == null || digits.isEmpty()) return false;
        char first = digits.charAt(0);
        for (int i = 1; i < digits.length(); i++) {
            if (digits.charAt(i) != first) return false;
        }
        return true;
    }

    public static boolean isCpfValido(CharSequence value) {
        if (isBlank(value)) return true;
        String cpf = somenteDigitos(value);
        if (!somenteNumerosEComprimento(cpf, 11)) return false;
        if (todosOsDigitosIguais(cpf)) return false;

        int dv1 = calcularDvCpf(cpf, 9, 10);
        int dv2 = calcularDvCpf(cpf, 10, 11);
        return cpf.charAt(9) == (char) ('0' + dv1) && cpf.charAt(10) == (char) ('0' + dv2);
    }

    private static int calcularDvCpf(String cpf, int len, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < len; i++) {
            int digito = cpf.charAt(i) - '0';
            soma += digito * (pesoInicial - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    public static boolean isCnpjValido(CharSequence value) {
        if (isBlank(value)) return true;
        String cnpj = somenteDigitos(value);
        if (!somenteNumerosEComprimento(cnpj, 14)) return false;
        if (todosOsDigitosIguais(cnpj)) return false;

        int dv1 = calcularDvCnpj(cnpj, 12);
        int dv2 = calcularDvCnpj(cnpj, 13);
        return cnpj.charAt(12) == (char) ('0' + dv1) && cnpj.charAt(13) == (char) ('0' + dv2);
    }

    private static int calcularDvCnpj(String cnpj, int len) {
        int soma = 0;
        int peso = 2;
        for (int i = len - 1; i >= 0; i--) {
            int digito = cnpj.charAt(i) - '0';
            soma += digito * peso;
            peso++;
            if (peso > 9) peso = 2;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    /**
     * Regra oficial (Ministério da Saúde / e-SUS AB):
     * - CNS tem 15 dígitos
     * - Inicia com 1 ou 2 (derivado do PIS/PASEP): validar cálculo com pesos 15..5 e sufixo 000/001 + DV
     * - Inicia com 7, 8 ou 9 (gerado): soma ponderada (pesos 15..1) % 11 == 0
     */
    public static boolean isCnsValido(CharSequence value) {
        if (isBlank(value)) return true;
        String cns = somenteDigitos(value);
        if (!somenteNumerosEComprimento(cns, 15)) return false;

        char first = cns.charAt(0);
        if (first == '1' || first == '2') {
            String pis = cns.substring(0, 11);
            String esperado = gerarCnsAPartirPis(pis);
            return cns.equals(esperado);
        }

        if (first == '7' || first == '8' || first == '9') {
            int soma = 0;
            for (int i = 0; i < 15; i++) {
                int digito = cns.charAt(i) - '0';
                int peso = 15 - i;
                soma += digito * peso;
            }
            return soma % 11 == 0;
        }

        return false;
    }

    /**
     * Gera CNS válido a partir de PIS (11 dígitos) para CNS iniciados por 1/2.
     * Algoritmo e-SUS AB:
     * - soma = Σ(pis[i] * (15-i)) com pesos 15..5
     * - dv = 11 - (soma % 11)
     * - se dv == 11 => dv=0 e sufixo \"000\"
     * - se dv == 10 => ajustar soma += 2; dv = 11 - (soma % 11); sufixo \"001\"
     * - caso contrário sufixo \"000\"
     * - CNS = PIS + sufixo + dv
     */
    public static String gerarCnsAPartirPis(String pis11Digitos) {
        if (!somenteNumerosEComprimento(pis11Digitos, 11)) return null;

        int soma = 0;
        for (int i = 0; i < 11; i++) {
            int digito = pis11Digitos.charAt(i) - '0';
            int peso = 15 - i;
            soma += digito * peso;
        }

        int resto = soma % 11;
        int dv = 11 - resto;
        String sufixo = "000";

        if (dv == 11) {
            dv = 0;
            sufixo = "000";
        } else if (dv == 10) {
            soma += 2;
            resto = soma % 11;
            dv = 11 - resto;
            sufixo = "001";
        }

        return pis11Digitos + sufixo + dv;
    }

    public static boolean isCnesValido(CharSequence value) {
        if (isBlank(value)) return true;
        String cnes = somenteDigitos(value);
        return somenteNumerosEComprimento(cnes, 7);
    }
}
