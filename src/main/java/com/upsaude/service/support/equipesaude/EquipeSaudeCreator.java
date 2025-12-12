package com.upsaude.service.support.equipesaude;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.EquipeSaudeMapper;
import com.upsaude.repository.EquipeSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeSaudeCreator {

    private final EquipeSaudeRepository repository;
    private final EquipeSaudeMapper mapper;
    private final EquipeSaudeValidationService validationService;
    private final EquipeSaudeRelacionamentosHandler relacionamentosHandler;

    public EquipeSaude criar(EquipeSaudeRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, repository, tenantId);

        EquipeSaude equipe = mapper.fromRequest(request);
        equipe.setActive(true);

        relacionamentosHandler.processarRelacionamentos(equipe, request, tenantId, Objects.requireNonNull(tenant, "tenant é obrigatório para criar equipe"));

        EquipeSaude salvo = repository.save(Objects.requireNonNull(equipe));
        log.info("Equipe de saúde criada com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
