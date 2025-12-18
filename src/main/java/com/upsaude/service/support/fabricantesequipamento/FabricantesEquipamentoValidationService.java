package com.upsaude.service.support.fabricantesequipamento;

import com.upsaude.api.request.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.estabelecimento.equipamento.FabricantesEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoValidationService {

    private final FabricantesEquipamentoRepository repository;

    public void validarObrigatorios(FabricantesEquipamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricante de equipamento são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do fabricante de equipamento é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(FabricantesEquipamentoRequest request) {
        validarUnicidade(null, request);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, FabricantesEquipamentoRequest request) {
        validarUnicidade(id, request);
    }

    private void validarUnicidade(UUID id, FabricantesEquipamentoRequest request) {
        if (request == null) return;

        if (StringUtils.hasText(request.getNome())) {
            String nome = request.getNome().trim();
            boolean duplicado = (id == null) ? repository.existsByNome(nome) : repository.existsByNomeAndIdNot(nome, id);
            if (duplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de equipamento com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(String.format(
                    "Já existe um fabricante de equipamento cadastrado com o nome '%s' no banco de dados", request.getNome()
                ));
            }
        }

        if (StringUtils.hasText(request.getCnpj())) {
            String cnpj = request.getCnpj().trim();
            boolean duplicado = (id == null) ? repository.existsByCnpj(cnpj) : repository.existsByCnpjAndIdNot(cnpj, id);
            if (duplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de equipamento com CNPJ duplicado. CNPJ: {}", request.getCnpj());
                throw new BadRequestException(String.format(
                    "Já existe um fabricante de equipamento cadastrado com o CNPJ '%s' no banco de dados", request.getCnpj()
                ));
            }
        }
    }
}

