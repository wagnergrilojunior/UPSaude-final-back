package com.upsaude.service.api.support.equipamentosestabelecimento;

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoValidationService {

    private final EquipamentosEstabelecimentoRepository repository;

    public void validarObrigatorios(EquipamentosEstabelecimentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do equipamento do estabelecimento são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getEquipamento() == null) {
            throw new BadRequestException("Equipamento é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(EquipamentosEstabelecimentoRequest request, UUID tenantId) {
        if (request.getEstabelecimento() != null && request.getEquipamento() != null 
            && StringUtils.hasText(request.getNumeroSerie())) {
            boolean duplicado = repository.findByEstabelecimentoIdAndTenantIdAndNumeroSerie(
                request.getEstabelecimento(), tenantId, request.getNumeroSerie()).isPresent();
            if (duplicado) {
                throw new ConflictException("Já existe um equipamento cadastrado com o número de série informado: " + request.getNumeroSerie());
            }
        }
    }

    public void validarUnicidadeParaAtualizacao(UUID id, EquipamentosEstabelecimentoRequest request, UUID tenantId) {
        if (request.getEstabelecimento() != null && request.getEquipamento() != null 
            && StringUtils.hasText(request.getNumeroSerie())) {
            var existente = repository.findByEstabelecimentoIdAndTenantIdAndNumeroSerie(
                request.getEstabelecimento(), tenantId, request.getNumeroSerie());
            if (existente.isPresent() && !existente.get().getId().equals(id)) {
                throw new ConflictException("Já existe um equipamento cadastrado com o número de série informado: " + request.getNumeroSerie());
            }
        }
    }
}
