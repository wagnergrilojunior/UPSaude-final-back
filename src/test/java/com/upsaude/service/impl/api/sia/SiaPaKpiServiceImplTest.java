package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.service.api.cnes.CnesEstabelecimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.relatorios.TenantFilterHelper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class SiaPaKpiServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private TenantService tenantService;

    @Mock
    private TenantFilterHelper tenantFilterHelper;

    @Mock
    private CnesEstabelecimentoService cnesEstabelecimentoService;

    @Mock
    private EstabelecimentosRepository estabelecimentosRepository;

    @Mock
    private MedicosRepository medicosRepository;

    @InjectMocks
    private SiaPaKpiServiceImpl service;

    @Test
    void deveCalcularTaxasBasicasETentarPerCapita() {
        Tenant tenant = new Tenant();
        Endereco endereco = new Endereco();
        Estados estado = new Estados();
        estado.setSigla("MG");
        endereco.setEstado(estado);
        endereco.setCodigoIbgeMunicipio("3106200");

        Cidades cidade = new Cidades();
        cidade.setPopulacaoEstimada(1000);
        endereco.setCidade(cidade);
        tenant.setEndereco(endereco);

        when(tenantService.obterTenantDoUsuarioAutenticado()).thenReturn(tenant);
        when(tenantFilterHelper.obterCnesDoTenant(any())).thenReturn(List.of("1234567"));

        Map<String, Object> baseRow = new HashMap<>();
        baseRow.put("total_registros", 10L);
        baseRow.put("procedimentos_unicos", 3L);
        baseRow.put("estabelecimentos_unicos", 2L);
        baseRow.put("quantidade_produzida_total", 50L);
        baseRow.put("quantidade_aprovada_total", 40L);
        baseRow.put("valor_produzido_total", new BigDecimal("100.00"));
        baseRow.put("valor_aprovado_total", new BigDecimal("80.00"));
        baseRow.put("registros_com_erro", 2L);

        Map<String, Object> munRow = new HashMap<>();
        munRow.put("quantidade_produzida_total", 100L);

        when(jdbcTemplate.queryForMap(anyString(), any(Object[].class))).thenAnswer(invocation -> {
            String sql = invocation.getArgument(0, String.class);
            if (sql.contains("municipio_gestao_codigo")) return munRow;
            return baseRow;
        });

        SiaPaKpiResponse resp = service.kpiGeral("202501", null);

        assertEquals("202501", resp.getCompetencia());
        assertEquals("MG", resp.getUf());
        assertEquals(10L, resp.getTotalRegistros());
        assertEquals(new BigDecimal("0.200000"), resp.getTaxaErroRegistros());
        assertEquals(new BigDecimal("0.800000"), resp.getTaxaAprovacaoValor());
        assertNotNull(resp.getProducaoPerCapita());
    }
}

