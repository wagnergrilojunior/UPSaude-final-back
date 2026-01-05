package com.upsaude.service.api.support.profissionaissaude;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeOneToOneRelationshipHandler {

    private final EnderecoRepository enderecoRepository;
    private final SigtapCboRepository sigtapCboRepository;

    public ProfissionaisSaude processarRelacionamentos(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request, Tenant tenant) {
        processarEndereco(profissional, request, tenant);
        processarSigtapOcupacao(profissional, request);
        return profissional;
    }

    private void processarSigtapOcupacao(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request) {
        if (request.getSigtapOcupacao() != null) {
            log.debug("Processando sigtap ocupação: {}", request.getSigtapOcupacao());
            SigtapOcupacao sigtapOcupacao = sigtapCboRepository.findById(Objects.requireNonNull(request.getSigtapOcupacao()))
                    .orElseThrow(() -> new NotFoundException("SIGTAP Ocupação não encontrada com ID: " + request.getSigtapOcupacao()));
            profissional.setSigtapOcupacao(sigtapOcupacao);
        } else {
            log.debug("SIGTAP Ocupação não fornecida. Mantendo ocupação existente.");
        }
    }

    private void processarEndereco(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request, Tenant tenant) {
        if (request.getEnderecoProfissional() != null) {
            log.debug("Processando endereço profissional como UUID: {}", request.getEnderecoProfissional());
            Endereco enderecoProfissional = enderecoRepository.findById(Objects.requireNonNull(request.getEnderecoProfissional()))
                    .orElseThrow(() -> new NotFoundException("Endereço profissional não encontrado com ID: " + request.getEnderecoProfissional()));
            profissional.setEnderecoProfissional(enderecoProfissional);
        } else {
            log.debug("Endereço profissional não fornecido. Mantendo endereço existente.");
        }
    }
}
