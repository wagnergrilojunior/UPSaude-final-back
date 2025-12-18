package com.upsaude.cache;

import com.upsaude.service.sistema.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

@Component("convenioCacheKeyGenerator")
@RequiredArgsConstructor
public class ConvenioCacheKeyGenerator implements KeyGenerator {

    private final TenantService tenantService;

    @Override
    public @NonNull Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        UUID tenantId = tenantService.validarTenantAtual();

        if (params.length == 0 || !(params[0] instanceof UUID)) {
            throw new IllegalArgumentException("Não foi possível gerar cache key para '" + method.getName()
                + "': esperado UUID como primeiro parâmetro");
        }

        UUID id = (UUID) params[0];
        return Objects.requireNonNull((Object) CacheKeyUtil.convenio(tenantId, id));
    }
}

