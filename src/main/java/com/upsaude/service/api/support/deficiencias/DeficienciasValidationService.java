package com.upsaude.service.api.support.deficiencias;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.deficiencia.DeficienciasRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.paciente.deficiencia.DeficienciasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasValidationService {

    private final DeficienciasRepository repository;

    public void validarObrigatorios(DeficienciasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da deficiência são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome da deficiência é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(DeficienciasRequest request) {
        validarUnicidade(null, request);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, DeficienciasRequest request) {
        validarUnicidade(id, request);
    }

    private void validarUnicidade(UUID id, DeficienciasRequest request) {
        if (request == null) return;

        if (StringUtils.hasText(request.getNome())) {
            String nome = request.getNome().trim();
            boolean duplicado = (id == null) ? repository.existsByNome(nome) : repository.existsByNomeAndIdNot(nome, id);
            if (duplicado) {
                log.warn("Tentativa de cadastrar/atualizar deficiência com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(String.format(
                    "Já existe uma deficiência cadastrada com o nome '%s' no banco de dados", request.getNome()
                ));
            }
        }
    }
}
