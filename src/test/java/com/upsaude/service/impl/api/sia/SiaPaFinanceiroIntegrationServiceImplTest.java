package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroIntegracaoResponse;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class SiaPaFinanceiroIntegrationServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private TenantService tenantService;

    @Mock
    private EstabelecimentosRepository estabelecimentosRepository;

    @InjectMocks
    private SiaPaFinanceiroIntegrationServiceImpl service;

    @Test
    void conciliacaoDeveRetornarZerosQuandoTenantNaoTemEstabelecimentos() {
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID());

        when(tenantService.obterTenantDoUsuarioAutenticado()).thenReturn(tenant);
        when(estabelecimentosRepository.findByTenant(tenant)).thenReturn(Collections.emptyList());
        when(jdbcTemplate.queryForList(anyString(), any(Object[].class))).thenReturn(Collections.emptyList());

        SiaPaFinanceiroIntegracaoResponse resp = service.conciliacao("202501", "MG", 10);
        assertEquals(0L, resp.getSiaQuantidadeTotal());
        assertEquals(0L, resp.getFaturamentoQuantidadeTotal());
    }
}

