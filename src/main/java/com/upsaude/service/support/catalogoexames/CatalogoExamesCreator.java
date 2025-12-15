package com.upsaude.service.support.catalogoexames;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.CatalogoExamesMapper;
import com.upsaude.repository.CatalogoExamesRepository;
import com.upsaude.service.support.catalogoexames.CatalogoExamesValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoExamesCreator {

    private final CatalogoExamesRepository repository;
    private final CatalogoExamesMapper mapper;
    private final CatalogoExamesValidationService validationService;

    public CatalogoExames criar(CatalogoExamesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, repository, tenantId);

        CatalogoExames exame = mapper.fromRequest(request);
        exame.setActive(true);
        exame.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar exame no catálogo"));

        CatalogoExames salvo = repository.save(Objects.requireNonNull(exame));
        log.info("Exame criado no catálogo com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
