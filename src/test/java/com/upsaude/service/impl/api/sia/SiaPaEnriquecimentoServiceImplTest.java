package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.SiaPaEnriquecidoResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoEstabelecimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.referencia.sia.SiaPaEnriquecido;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.referencia.sia.SiaPaEnriquecidoRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class SiaPaEnriquecimentoServiceImplTest {

    @Mock
    private SiaPaEnriquecidoRepository repository;

    @Mock
    private TenantService tenantService;

    @Mock
    private EstabelecimentosRepository estabelecimentosRepository;

    @InjectMocks
    private SiaPaEnriquecimentoServiceImpl service;

    @Test
    void deveEnriquecerNomeDoEstabelecimentoPeloTenantQuandoDisponivel() {
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID());

        SiaPaEnriquecido e = new SiaPaEnriquecido();
        e.setId(UUID.randomUUID());
        e.setCompetencia("202501");
        e.setUf("MG");
        e.setCodigoCnes("2530031");
        e.setEstabelecimentoNome("NomeDescritivoView");

        Page<SiaPaEnriquecido> page = new PageImpl<>(List.of(e), PageRequest.of(0, 10), 1);

        Estabelecimentos estab = new Estabelecimentos();
        DadosIdentificacaoEstabelecimento dados = new DadosIdentificacaoEstabelecimento();
        dados.setNome("NomeDoTenant");
        dados.setCnes("2530031");
        estab.setDadosIdentificacao(dados);

        when(tenantService.obterTenantDoUsuarioAutenticado()).thenReturn(tenant);
        when(repository.findByCompetenciaAndUf("202501", "MG", PageRequest.of(0, 10))).thenReturn(page);
        when(estabelecimentosRepository.findByCodigoCnesAndTenant("2530031", tenant)).thenReturn(Optional.of(estab));

        Page<SiaPaEnriquecidoResponse> resp = service.listarEnriquecido("202501", "MG", null, null, PageRequest.of(0, 10));

        assertEquals(1, resp.getTotalElements());
        assertEquals("NomeDoTenant", resp.getContent().get(0).getEstabelecimentoNome());
    }
}

