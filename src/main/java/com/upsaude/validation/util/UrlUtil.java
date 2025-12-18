package com.upsaude.validation.util;

import java.net.URI;

public final class UrlUtil {

    private UrlUtil() {}

    public static boolean isSiteValido(CharSequence value) {
        if (DocumentoUtil.isBlank(value)) return true;
        String raw = value.toString().trim();
        try {
            URI uri = URI.create(raw);
            String scheme = uri.getScheme();
            if (scheme == null) return false;
            String s = scheme.toLowerCase();
            if (!s.equals("http") && !s.equals("https")) return false;
            String host = uri.getHost();
            if (host == null || host.isBlank()) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
