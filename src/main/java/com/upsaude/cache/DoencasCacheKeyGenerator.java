package com.upsaude.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

@Component("doencasCacheKeyGenerator")
@RequiredArgsConstructor
public class DoencasCacheKeyGenerator implements KeyGenerator {

    @Override
    public @NonNull Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        if (params.length == 0 || !(params[0] instanceof UUID)) {
            throw new IllegalArgumentException("Não foi possível gerar cache key para '" + method.getName()
                + "': esperado UUID como primeiro parâmetro");
        }

        UUID id = (UUID) params[0];
        return Objects.requireNonNull((Object) CacheKeyUtil.doenca(id));
    }
}

