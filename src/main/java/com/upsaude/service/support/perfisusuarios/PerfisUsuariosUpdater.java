package com.upsaude.service.support.perfisusuarios;

import com.upsaude.api.request.sistema.PerfisUsuariosRequest;
import com.upsaude.entity.sistema.PerfisUsuarios;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.PerfisUsuariosMapper;
import com.upsaude.repository.sistema.PerfisUsuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfisUsuariosUpdater {

    private final PerfisUsuariosRepository repository;
    private final PerfisUsuariosMapper mapper;
    private final PerfisUsuariosTenantEnforcer tenantEnforcer;
    private final PerfisUsuariosValidationService validationService;
    private final PerfisUsuariosRelacionamentosHandler relacionamentosHandler;

    public PerfisUsuarios atualizar(UUID id, PerfisUsuariosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        PerfisUsuarios entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.validarEProcessarUsuario(request, entity, tenantId, tenant);

        PerfisUsuarios saved = repository.save(Objects.requireNonNull(entity));
        log.info("Perfil de usuário atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

