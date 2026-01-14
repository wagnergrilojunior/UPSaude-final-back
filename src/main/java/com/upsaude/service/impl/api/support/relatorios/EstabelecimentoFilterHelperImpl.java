package com.upsaude.service.impl.api.support.relatorios;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.support.relatorios.EstabelecimentoFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentoFilterHelperImpl implements EstabelecimentoFilterHelper {

    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    public boolean validarEstabelecimentoPertenceAoTenant(UUID estabelecimentoId, UUID tenantId) {
        log.debug("Validando se estabelecimento {} pertence ao tenant {}", estabelecimentoId, tenantId);
        
        if (estabelecimentoId == null || tenantId == null) {
            return false;
        }
        
        boolean pertence = estabelecimentosRepository.findByIdAndTenant(estabelecimentoId, tenantId)
                .isPresent();
        
        log.debug("Estabelecimento {} {} ao tenant {}", estabelecimentoId, pertence ? "pertence" : "não pertence", tenantId);
        return pertence;
    }
}
