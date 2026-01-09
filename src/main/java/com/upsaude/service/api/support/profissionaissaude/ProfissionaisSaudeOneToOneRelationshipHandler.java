package com.upsaude.service.api.support.profissionaissaude;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import com.upsaude.service.api.geral.EnderecoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeOneToOneRelationshipHandler {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final CidadesRepository cidadesRepository;
    private final EstadosRepository estadosRepository;
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
            log.debug("Processando endereço profissional como objeto completo");
            
            EnderecoRequest enderecoRequest = request.getEnderecoProfissional();
            boolean temCamposPreenchidos = (enderecoRequest.getLogradouro() != null && !enderecoRequest.getLogradouro().trim().isEmpty()) ||
                (enderecoRequest.getCep() != null && !enderecoRequest.getCep().trim().isEmpty()) ||
                (enderecoRequest.getCidade() != null) ||
                (enderecoRequest.getEstado() != null);
            
            if (!temCamposPreenchidos) {
                log.warn("Endereço profissional fornecido mas sem campos preenchidos. Ignorando endereço.");
                profissional.setEnderecoProfissional(null);
                return;
            }
            
            if (profissional.getEnderecoProfissional() != null && profissional.getEnderecoProfissional().getId() != null) {
                log.debug("Atualizando endereço profissional existente. ID: {}", profissional.getEnderecoProfissional().getId());
                Endereco enderecoExistente = profissional.getEnderecoProfissional();
                enderecoMapper.updateFromRequest(enderecoRequest, enderecoExistente);
                
                if (enderecoExistente.getSemNumero() == null) {
                    enderecoExistente.setSemNumero(false);
                }
                
                if (enderecoRequest.getEstado() != null) {
                    Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                    enderecoExistente.setEstado(estado);
                } else {
                    enderecoExistente.setEstado(null);
                }
                
                if (enderecoRequest.getCidade() != null) {
                    Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                    enderecoExistente.setCidade(cidade);
                } else {
                    enderecoExistente.setCidade(null);
                }
                
                Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);
                profissional.setEnderecoProfissional(enderecoAtualizado);
                return;
            }
            
            Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
            endereco.setActive(true);
            endereco.setTenant(tenant);
            
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }
            
            if (enderecoRequest.getEstado() != null) {
                Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                    .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                endereco.setEstado(estado);
            }
            
            if (enderecoRequest.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                    .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                endereco.setCidade(cidade);
            }
            
            if ((endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) &&
                (endereco.getCep() == null || endereco.getCep().trim().isEmpty())) {
                log.debug("Endereço sem logradouro nem CEP. Criando novo endereço sem buscar duplicados.");
                Endereco enderecoSalvo = enderecoRepository.save(endereco);
                profissional.setEnderecoProfissional(enderecoSalvo);
                return;
            }
            
            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            profissional.setEnderecoProfissional(enderecoProcessado);
        } else {
            log.debug("Endereço profissional não fornecido. Mantendo endereço existente.");
        }
    }
}
