package com.upsaude.service.support.configuracaoestabelecimento;

import com.upsaude.api.request.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.ConfiguracaoEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoValidationService {

    private final ConfiguracaoEstabelecimentoRepository repository;

    public void validarObrigatorios(ConfiguracaoEstabelecimentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da configuração do estabelecimento são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
    }

    public void validarUnicidadePorEstabelecimento(UUID estabelecimentoId, UUID tenantId) {
        if (repository.existsByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId)) {
            throw new ConflictException("Já existe uma configuração para este estabelecimento");
        }
    }
}

