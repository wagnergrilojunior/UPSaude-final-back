package com.upsaude.service.impl.api.sia;

import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.referencia.sia.anomalia.SiaPaAnomaliaRepository;
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
class SiaPaAnomaliaDetectionServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private SiaPaAnomaliaRepository repository;

    @InjectMocks
    private SiaPaAnomaliaDetectionServiceImpl service;

    @Test
    void deveFalharSemParametrosObrigatorios() {
        assertThrows(BadRequestException.class, () -> service.detectar(null, "MG"));
        assertThrows(BadRequestException.class, () -> service.detectar("202501", null));
    }

    @Test
    void deveExecutarDeteccoesBasicasERetornarContagem() {
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(jdbcTemplate.queryForMap(anyString(), any(Object[].class))).thenReturn(Map.of(
                "media", new BigDecimal("100.00"),
                "desvio", new BigDecimal("10.00")
        ));

        int inseridas = service.detectar("202501", "MG");
        // 3 inserções (uma por regra) de acordo com stubs
        assertEquals(3, inseridas);
    }
}

