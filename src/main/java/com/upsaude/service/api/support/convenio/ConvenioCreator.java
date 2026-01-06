package com.upsaude.service.api.support.convenio;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.repository.convenio.ConvenioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioCreator {

    private final ConvenioRepository repository;
    private final ConvenioMapper mapper;
    private final ConvenioValidationService validationService;
    private final ConvenioRelacionamentosHandler relacionamentosHandler;

    public Convenio criar(ConvenioRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        Convenio entity = mapper.fromRequest(request);
        entity.setActive(true);

        if (entity.getCoberturaObstetricia() == null) {
            entity.setCoberturaObstetricia(false);
        }
        if (entity.getHabilitadoTiss() == null) {
            entity.setHabilitadoTiss(false);
        }
        if (entity.getSincronizarAns() == null) {
            entity.setSincronizarAns(false);
        }
        if (entity.getSincronizarSus() == null) {
            entity.setSincronizarSus(false);
        }
        if (entity.getSincronizarTiss() == null) {
            entity.setSincronizarTiss(false);
        }
        if (entity.getRedeCredenciadaNacional() == null) {
            entity.setRedeCredenciadaNacional(false);
        }
        if (entity.getRedeCredenciadaRegional() == null) {
            entity.setRedeCredenciadaRegional(false);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(com.upsaude.enums.StatusAtivoEnum.ATIVO);
        }

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Convenio saved = repository.save(Objects.requireNonNull(entity));
        log.info("Convênio criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
