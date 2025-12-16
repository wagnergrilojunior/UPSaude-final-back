package com.upsaude.service.support.perfisusuarios;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.UsuariosSistemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfisUsuariosRelacionamentosHandler {

    private final UsuariosSistemaRepository usuariosSistemaRepository;

    public PerfisUsuarios validarEProcessarUsuario(PerfisUsuariosRequest request,
                                                  PerfisUsuarios entity,
                                                  UUID tenantId,
                                                  Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        if (request == null || request.getUsuarioId() == null) {
            throw new BadRequestException("usuarioId é obrigatório");
        }

        UsuariosSistema usuario = usuariosSistemaRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new NotFoundException("Usuário do sistema não encontrado com ID: " + request.getUsuarioId()));

        if (usuario.getTenant() == null || usuario.getTenant().getId() == null || !tenantId.equals(usuario.getTenant().getId())) {
            // não vazar info de outro tenant
            throw new NotFoundException("Usuário do sistema não encontrado com ID: " + request.getUsuarioId());
        }

        entity.setTenant(tenant);

        // BaseEntity.estabelecimento (opcional): manter o que já estiver setado; request não tem campo
        return entity;
    }
}

