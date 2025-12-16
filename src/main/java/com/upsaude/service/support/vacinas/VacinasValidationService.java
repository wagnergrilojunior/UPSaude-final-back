package com.upsaude.service.support.vacinas;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.VacinasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VacinasValidationService {

    private final VacinasRepository repository;

    public void validarObrigatorios(VacinasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da vacina são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome da vacina é obrigatório");
        }
    }

    public void validarDuplicidade(UUID id, VacinasRequest request) {
        if (request == null) {
            return;
        }

        if (StringUtils.hasText(request.getNome())) {
            String nome = request.getNome().trim();
            boolean duplicado = (id == null)
                    ? repository.existsByNome(nome)
                    : repository.existsByNomeAndIdNot(nome, id);
            if (duplicado) {
                throw new BadRequestException(String.format(
                        "Já existe uma vacina cadastrada com o nome '%s' no banco de dados",
                        request.getNome()));
            }
        }

        if (StringUtils.hasText(request.getCodigoInterno())) {
            String codigo = request.getCodigoInterno().trim();
            boolean duplicado = (id == null)
                    ? repository.existsByCodigoInterno(codigo)
                    : repository.existsByCodigoInternoAndIdNot(codigo, id);
            if (duplicado) {
                throw new BadRequestException(String.format(
                        "Já existe uma vacina cadastrada com o código interno '%s' no banco de dados",
                        request.getCodigoInterno()));
            }
        }
    }
}
