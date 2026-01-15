package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.analytics.SiaPaComparacaoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class SiaPaAnalyticsServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private SiaPaAnalyticsServiceImpl service;

    @Test
    void deveFalharSemUf() {
        assertThrows(BadRequestException.class, () -> service.compararPeriodos(null, "202501", "202502"));
    }

    @Test
    void deveCompararPeriodos() {
        when(jdbcTemplate.queryForMap(anyString(), any(Object[].class))).thenAnswer(invocation -> {
            Object[] allArgs = invocation.getArguments();
            // Mockito pode “expandir” varargs em argumentos individuais
            String competencia = allArgs.length >= 3 ? String.valueOf(allArgs[2]) : null;
            if ("202501".equals(competencia)) {
                return Map.of("valor_aprovado_total", new BigDecimal("100.00"), "quantidade_produzida_total", 10L);
            }
            return Map.of("valor_aprovado_total", new BigDecimal("120.00"), "quantidade_produzida_total", 12L);
        });

        SiaPaComparacaoResponse resp = service.compararPeriodos("MG", "202501", "202502");
        assertEquals(new BigDecimal("20.00"), resp.getDeltaValorAprovado());
        assertEquals(2L, resp.getDeltaQuantidadeProduzida());
    }
}

