package com.upsaude.service.api.support.perfisusuarios;

import com.upsaude.api.request.sistema.usuario.PerfisUsuariosRequest;
import com.upsaude.entity.sistema.usuario.PerfisUsuarios;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.sistema.usuario.PerfisUsuariosMapper;
import com.upsaude.repository.sistema.usuario.PerfisUsuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfisUsuariosCreator {

    private final PerfisUsuariosRepository repository;
    private final PerfisUsuariosMapper mapper;
    private final PerfisUsuariosValidationService validationService;
    private final PerfisUsuariosRelacionamentosHandler relacionamentosHandler;

    public PerfisUsuarios criar(PerfisUsuariosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        PerfisUsuarios entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.validarEProcessarUsuario(request, entity, tenantId, tenant);

        PerfisUsuarios saved = repository.save(Objects.requireNonNull(entity));
        log.info("Perfil de usuário criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

