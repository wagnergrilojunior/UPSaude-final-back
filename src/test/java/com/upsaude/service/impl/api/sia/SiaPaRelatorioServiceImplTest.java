package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.relatorios.TenantFilterHelper;
import com.upsaude.service.api.support.sia.SiaDataEnricher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"null", "unchecked"})
class SiaPaRelatorioServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private TenantService tenantService;

    @Mock
    private TenantFilterHelper tenantFilterHelper;

    @Mock
    private SiaDataEnricher siaDataEnricher;

    @InjectMocks
    private SiaPaRelatorioServiceImpl service;

    @Test
    void deveFalharSeUfNaoForInformadaENaoForInferivel() {
        assertThrows(BadRequestException.class, () -> service.gerarTopProcedimentos(null, "202501", 10));
    }

    @Test
    void deveRetornarTopProcedimentos() {
        when(tenantFilterHelper.obterCnesDoTenant(any())).thenReturn(List.of("1234567"));
        
        var item = SiaPaRelatorioTopProcedimentosResponse.ItemTopProcedimento.builder()
                .procedimentoCodigo("0301010010")
                .procedimentoNome("CONSULTA")
                .quantidadeProduzidaTotal(100L)
                .valorAprovadoTotal(new BigDecimal("500.00"))
                .estabelecimentosUnicos(10L)
                .municipiosUnicos(5L)
                .build();

        doReturn(List.of(item))
                .when(jdbcTemplate)
                .query(anyString(), any(RowMapper.class), any(Object[].class));

        SiaPaRelatorioTopProcedimentosResponse resp = service.gerarTopProcedimentos("MG", "202501", 10);
        assertEquals(1, resp.getItens().size());
        assertEquals("0301010010", resp.getItens().get(0).getProcedimentoCodigo());
    }

    @Test
    void deveRetornarTopCid() {
        when(tenantFilterHelper.obterCnesDoTenant(any())).thenReturn(List.of("1234567"));
        
        var item = SiaPaRelatorioTopCidResponse.ItemTopCid.builder()
                .cidPrincipalCodigo("I10")
                .cidDescricao("Hipertensão")
                .quantidadeProduzidaTotal(50L)
                .valorAprovadoTotal(new BigDecimal("200.00"))
                .build();

        doReturn(List.of(item))
                .when(jdbcTemplate)
                .query(anyString(), any(RowMapper.class), any(Object[].class));

        SiaPaRelatorioTopCidResponse resp = service.gerarTopCid("MG", "202501", 10);
        assertEquals(1, resp.getItens().size());
        assertEquals("I10", resp.getItens().get(0).getCidPrincipalCodigo());
    }
}

