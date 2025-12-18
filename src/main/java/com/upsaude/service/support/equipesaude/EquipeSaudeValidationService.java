package com.upsaude.service.support.equipesaude;

import com.upsaude.api.request.profissional.equipe.EquipeSaudeRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class EquipeSaudeValidationService {

    public void validarObrigatorios(EquipeSaudeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da equipe de saúde são obrigatórios");
        }
        if (!StringUtils.hasText(request.getIne())) {
            throw new BadRequestException("INE é obrigatório");
        }
        if (!StringUtils.hasText(request.getNomeReferencia())) {
            throw new BadRequestException("Nome de referência é obrigatório");
        }
        if (request.getTipoEquipe() == null) {
            throw new BadRequestException("Tipo de equipe é obrigatório");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getDataAtivacao() == null) {
            throw new BadRequestException("Data de ativação é obrigatória");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(EquipeSaudeRequest request, EquipeSaudeRepository repository, UUID tenantId) {
        validarIneUnico(null, request, repository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, EquipeSaudeRequest request, EquipeSaudeRepository repository, UUID tenantId) {
        validarIneUnico(id, request, repository, tenantId);
    }

    private void validarIneUnico(UUID id, EquipeSaudeRequest request, EquipeSaudeRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getIne()) || request.getEstabelecimento() == null) {
            return;
        }

        boolean duplicado = repository.findByIneAndEstabelecimentoIdAndTenantId(request.getIne().trim(), request.getEstabelecimento(), tenantId)
                .map(e -> id == null || !e.getId().equals(id))
                .orElse(false);

        if (duplicado) {
            throw new BadRequestException("Já existe uma equipe com o INE " + request.getIne() + " neste estabelecimento");
        }
    }
}
