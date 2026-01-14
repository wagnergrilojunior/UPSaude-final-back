package com.upsaude.service.impl.api.support.sia;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.service.api.support.sia.SiaDataEnricher;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaDataEnricherImpl implements SiaDataEnricher {

    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;
    private final Cid10SubcategoriasRepository cid10SubcategoriasRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final EstabelecimentosMapper estabelecimentosMapper;
    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;
    private final TenantService tenantService;

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "sigtapProcedimento",
            key = "#codigoProcedimento",
            unless = "#result == null"
    )
    public String enriquecerComProcedimento(String codigoProcedimento) {
        if (codigoProcedimento == null || codigoProcedimento.trim().isEmpty()) {
            return null;
        }

        try {
            return sigtapProcedimentoRepository
                    .findTopByCodigoOficialOrderByCompetenciaInicialDesc(codigoProcedimento.trim())
                    .map(SigtapProcedimento::getNome)
                    .orElse(null);
        } catch (Exception e) {
            log.warn("Erro ao buscar procedimento SIGTAP para código {}: {}", codigoProcedimento, e.getMessage());
            return null;
        }
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "cid10Subcategoria",
            key = "#codigoCid",
            unless = "#result == null"
    )
    public String enriquecerComCid(String codigoCid) {
        if (codigoCid == null || codigoCid.trim().isEmpty()) {
            return null;
        }

        try {
            return cid10SubcategoriasRepository
                    .findBySubcat(codigoCid.trim().toUpperCase())
                    .map(Cid10Subcategorias::getDescricao)
                    .orElse(null);
        } catch (Exception e) {
            log.warn("Erro ao buscar CID10 para código {}: {}", codigoCid, e.getMessage());
            return null;
        }
    }

    @Override
    public EstabelecimentosResponse enriquecerComEstabelecimento(String cnes) {
        if (cnes == null || cnes.trim().isEmpty()) {
            return null;
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            com.upsaude.entity.sistema.multitenancy.Tenant tenant = new com.upsaude.entity.sistema.multitenancy.Tenant();
            tenant.setId(tenantId);
            return estabelecimentosRepository
                    .findByCodigoCnesAndTenant(cnes.trim(), tenant)
                    .map(estabelecimentosMapper::toResponse)
                    .orElse(null);
        } catch (Exception e) {
            log.warn("Erro ao buscar estabelecimento para CNES {}: {}", cnes, e.getMessage());
            return null;
        }
    }

    @Override
    public ProfissionaisSaudeResponse enriquecerComMedico(String cns) {
        if (cns == null || cns.trim().isEmpty()) {
            return null;
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return profissionaisSaudeRepository
                    .findByCnsAndTenantId(cns.trim(), tenantId)
                    .map(profissionaisSaudeMapper::toResponse)
                    .orElse(null);
        } catch (Exception e) {
            log.warn("Erro ao buscar profissional para CNS {}: {}", cns, e.getMessage());
            return null;
        }
    }
}
