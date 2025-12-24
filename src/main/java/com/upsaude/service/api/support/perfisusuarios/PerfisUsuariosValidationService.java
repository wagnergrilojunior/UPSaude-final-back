package com.upsaude.service.api.support.perfisusuarios;

import com.upsaude.api.request.sistema.usuario.PerfisUsuariosRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.sistema.usuario.PerfisUsuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfisUsuariosValidationService {

    private final PerfisUsuariosRepository repository;

    public void validarObrigatorios(PerfisUsuariosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do perfil de usuário são obrigatórios");
        }
        if (request.getUsuarioId() == null) {
            throw new BadRequestException("usuarioId é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(PerfisUsuariosRequest request, UUID tenantId) {
        validarUnicidade(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, PerfisUsuariosRequest request, UUID tenantId) {
        validarUnicidade(id, request, tenantId);
    }

    private void validarUnicidade(UUID id, PerfisUsuariosRequest request, UUID tenantId) {
        if (request == null || request.getUsuarioId() == null) return;

        boolean duplicado = (id == null)
            ? repository.existsByUsuarioIdAndTenantId(request.getUsuarioId(), tenantId)
            : repository.existsByUsuarioIdAndTenantIdAndIdNot(request.getUsuarioId(), tenantId, id);

        if (duplicado) {
            log.warn("Tentativa de criar/atualizar perfil de usuário duplicado. usuarioId: {}, tenant: {}", request.getUsuarioId(), tenantId);
            throw new BadRequestException("Já existe um perfil cadastrado para este usuário no tenant atual");
        }
    }
}

