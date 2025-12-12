package com.upsaude.service.support.profissionaissaude;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estados;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.ConselhosProfissionaisRepository;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeOneToOneRelationshipHandler {

    private final ConselhosProfissionaisRepository conselhosProfissionaisRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    public ProfissionaisSaude processarRelacionamentos(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request, Tenant tenant) {
        processarConselho(profissional, request);
        processarEndereco(profissional, request, tenant);
        return profissional;
    }

    private void processarConselho(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request) {
        if (request.getConselho() != null) {
            ConselhosProfissionais conselho = conselhosProfissionaisRepository.findById(Objects.requireNonNull(request.getConselho()))
                    .orElseThrow(() -> new NotFoundException("Conselho profissional não encontrado com ID: " + request.getConselho()));
            profissional.setConselho(conselho);
        }
    }

    private void processarEndereco(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request, Tenant tenant) {
        if (request.getEnderecoProfissionalCompleto() != null) {
            log.debug("Processando endereço profissional como objeto completo. Usando findOrCreate para evitar duplicação");

            Endereco endereco = enderecoMapper.fromRequest(request.getEnderecoProfissionalCompleto());
            endereco.setActive(true);

            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
            }
            endereco.setTenant(tenant);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            if (request.getEnderecoProfissionalCompleto().getEstado() != null) {
                Estados estado = estadosRepository.findById(Objects.requireNonNull(request.getEnderecoProfissionalCompleto().getEstado()))
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEnderecoProfissionalCompleto().getEstado()));
                endereco.setEstado(estado);
            }

            if (request.getEnderecoProfissionalCompleto().getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(Objects.requireNonNull(request.getEnderecoProfissionalCompleto().getCidade()))
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + request.getEnderecoProfissionalCompleto().getCidade()));
                endereco.setCidade(cidade);
            }

            UUID idAntes = endereco.getId();
            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            profissional.setEnderecoProfissional(enderecoProcessado);

            boolean foiCriadoNovo = idAntes == null && enderecoProcessado.getId() != null;
            log.info("Endereço profissional processado. ID: {} - {}",
                    enderecoProcessado.getId(),
                    foiCriadoNovo ? "Novo endereço criado" : "Endereço existente reutilizado");

        } else if (request.getEnderecoProfissional() != null) {
            log.debug("Processando endereço profissional como UUID: {}", request.getEnderecoProfissional());
            Endereco enderecoProfissional = enderecoRepository.findById(Objects.requireNonNull(request.getEnderecoProfissional()))
                    .orElseThrow(() -> new NotFoundException("Endereço profissional não encontrado com ID: " + request.getEnderecoProfissional()));
            profissional.setEnderecoProfissional(enderecoProfissional);
        } else {
            log.debug("Endereço profissional não fornecido. Mantendo endereço existente.");
        }
    }
}
