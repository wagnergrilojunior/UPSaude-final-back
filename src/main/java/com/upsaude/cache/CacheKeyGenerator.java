package com.upsaude.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Gerador de chaves de cache para relatórios e KPIs.
 */
@Component("relatoriosCacheKeyGenerator")
public class CacheKeyGenerator implements KeyGenerator {

    @Override
    @NonNull
    public Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(target.getClass().getSimpleName());
        keyBuilder.append(".");
        keyBuilder.append(method.getName());
        keyBuilder.append("(");

        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                keyBuilder.append(",");
            }
            Object param = params[i];
            if (param == null) {
                keyBuilder.append("null");
            } else if (param instanceof UUID) {
                keyBuilder.append(param.toString());
            } else if (param instanceof String) {
                keyBuilder.append(param.toString().replaceAll("[^a-zA-Z0-9]", "_"));
            } else if (param instanceof java.time.LocalDate) {
                keyBuilder.append(param.toString());
            } else {
                keyBuilder.append(param.toString());
            }
        }

        keyBuilder.append(")");
        return keyBuilder.toString().intern();
    }
}
