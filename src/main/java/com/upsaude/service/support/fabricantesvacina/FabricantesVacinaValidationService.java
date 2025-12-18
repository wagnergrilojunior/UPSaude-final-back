package com.upsaude.service.support.fabricantesvacina;

import com.upsaude.api.request.saude_publica.vacina.FabricantesVacinaRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.saude_publica.vacina.FabricantesVacinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesVacinaValidationService {

    private final FabricantesVacinaRepository repository;

    public void validarObrigatorios(FabricantesVacinaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricante de vacina são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do fabricante de vacina é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(FabricantesVacinaRequest request) {
        validarUnicidade(null, request);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, FabricantesVacinaRequest request) {
        validarUnicidade(id, request);
    }

    private void validarUnicidade(UUID id, FabricantesVacinaRequest request) {
        if (request == null) return;

        if (StringUtils.hasText(request.getNome())) {
            String nome = request.getNome().trim();
            boolean duplicado = (id == null) ? repository.existsByNome(nome) : repository.existsByNomeAndIdNot(nome, id);
            if (duplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de vacina com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(String.format(
                    "Já existe um fabricante de vacina cadastrado com o nome '%s' no banco de dados", request.getNome()
                ));
            }
        }

        if (StringUtils.hasText(request.getCnpj())) {
            String cnpj = request.getCnpj().trim();
            boolean duplicado = (id == null) ? repository.existsByCnpj(cnpj) : repository.existsByCnpjAndIdNot(cnpj, id);
            if (duplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de vacina com CNPJ duplicado. CNPJ: {}", request.getCnpj());
                throw new BadRequestException(String.format(
                    "Já existe um fabricante de vacina cadastrado com o CNPJ '%s' no banco de dados", request.getCnpj()
                ));
            }
        }
    }
}

