package com.upsaude.service.support.ciddoencas;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.CidDoencasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CidDoencasValidationService {

    private final CidDoencasRepository repository;

    public void validarObrigatorios(CidDoencasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do CID de doença são obrigatórios");
        }
        if (!StringUtils.hasText(request.getCodigo())) {
            throw new BadRequestException("Código CID é obrigatório");
        }
        if (!StringUtils.hasText(request.getDescricao())) {
            throw new BadRequestException("Descrição é obrigatória");
        }
    }

    public void validarUnicidadeParaCriacao(String codigo) {
        if (!StringUtils.hasText(codigo)) return;
        if (repository.findByCodigo(codigo).isPresent()) {
            throw new ConflictException("Já existe um CID de doença com o código: " + codigo);
        }
    }

    public void validarUnicidadeParaAtualizacao(UUID id, String codigo) {
        if (id == null || !StringUtils.hasText(codigo)) return;
        if (repository.existsByCodigoAndIdNot(codigo, id)) {
            throw new ConflictException("Já existe outro CID de doença com o código: " + codigo);
        }
    }
}

