package com.upsaude.service.impl.api.support.relatorios;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.support.relatorios.TenantFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantFilterHelperImpl implements TenantFilterHelper {

    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    public List<String> obterCnesDoTenant(UUID tenantId) {
        log.debug("Buscando CNES dos estabelecimentos do tenant: {}", tenantId);
        
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        
        List<Estabelecimentos> estabelecimentos = estabelecimentosRepository.findByTenant(tenant);
        
        List<String> cnesList = estabelecimentos.stream()
                .filter(e -> e.getDadosIdentificacao() != null && e.getDadosIdentificacao().getCnes() != null)
                .map(e -> e.getDadosIdentificacao().getCnes())
                .filter(cnes -> cnes != null && !cnes.trim().isEmpty())
                .collect(Collectors.toList());
        
        log.debug("Encontrados {} CNES para o tenant {}", cnesList.size(), tenantId);
        return cnesList;
    }

    @Override
    public boolean validarCnesPertenceAoTenant(String cnes, UUID tenantId) {
        log.debug("Validando se CNES {} pertence ao tenant {}", cnes, tenantId);
        
        if (cnes == null || cnes.trim().isEmpty() || tenantId == null) {
            return false;
        }
        
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        
        boolean pertence = estabelecimentosRepository.findByCodigoCnesAndTenant(cnes, tenant)
                .isPresent();
        
        log.debug("CNES {} {} ao tenant {}", cnes, pertence ? "pertence" : "não pertence", tenantId);
        return pertence;
    }

    @Override
    public List<UUID> obterEstabelecimentosIdsDoTenant(UUID tenantId) {
        log.debug("Buscando IDs dos estabelecimentos do tenant: {}", tenantId);
        
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        
        List<UUID> ids = estabelecimentosRepository.findByTenant(tenant).stream()
                .map(Estabelecimentos::getId)
                .collect(Collectors.toList());
        
        log.debug("Encontrados {} estabelecimentos para o tenant {}", ids.size(), tenantId);
        return ids;
    }
}
