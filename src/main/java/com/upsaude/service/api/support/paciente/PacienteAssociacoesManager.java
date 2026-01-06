package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteEndereco;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.mapper.paciente.DadosClinicosBasicosMapper;
import com.upsaude.mapper.paciente.DadosSociodemograficosMapper;
import com.upsaude.mapper.paciente.PacienteContatoMapper;
import com.upsaude.mapper.paciente.PacienteDadosPessoaisComplementaresMapper;
import com.upsaude.mapper.paciente.PacienteEnderecoMapper;
import com.upsaude.mapper.paciente.PacienteIdentificadorMapper;
import com.upsaude.mapper.paciente.PacienteObitoMapper;
import com.upsaude.mapper.paciente.PacienteVinculoTerritorialMapper;
import com.upsaude.mapper.paciente.ResponsavelLegalMapper;
import com.upsaude.mapper.paciente.deficiencia.DeficienciasPacienteMapper;
import com.upsaude.mapper.sistema.lgpd.LGPDConsentimentoMapper;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.repository.paciente.deficiencia.DeficienciasRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.geral.EnderecoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteAssociacoesManager {

    private final EnderecoService enderecoService;
    private final DeficienciasRepository deficienciasRepository;
    private final ConvenioRepository convenioRepository;
    private final TenantRepository tenantRepository;

    private final PacienteEnderecoMapper pacienteEnderecoMapper;
    private final EnderecoMapper enderecoMapper;
    private final PacienteIdentificadorMapper pacienteIdentificadorMapper;
    private final PacienteContatoMapper pacienteContatoMapper;
    private final DadosSociodemograficosMapper dadosSociodemograficosMapper;
    private final DadosClinicosBasicosMapper dadosClinicosBasicosMapper;
    private final ResponsavelLegalMapper responsavelLegalMapper;
    private final PacienteDadosPessoaisComplementaresMapper dadosPessoaisComplementaresMapper;
    private final PacienteObitoMapper pacienteObitoMapper;
    private final DeficienciasPacienteMapper deficienciasPacienteMapper;
    private final PacienteVinculoTerritorialMapper pacienteVinculoTerritorialMapper;
    private final LGPDConsentimentoMapper lgpdConsentimentoMapper;

    @Transactional
    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações para paciente ID: {}", paciente.getId());

        Tenant tenant = tenantRepository.getReferenceById(tenantId);

        // 1. Processar Convênio
        if (request.getConvenio() != null) {
            convenioRepository.findById(request.getConvenio())
                    .ifPresent(paciente::setConvenio);
        }

        // 2. Processar Endereços
        if (request.getEnderecos() != null) {
            paciente.getEnderecos().clear();
            request.getEnderecos().forEach(req -> {
                PacienteEndereco entity = pacienteEnderecoMapper.fromRequest(req);
                entity.setPaciente(paciente);
                entity.setTenant(tenant);
                if (req.getDadosEndereco() != null) {
                    Endereco transientEndereco = enderecoMapper.fromRequest(req.getDadosEndereco());
                    transientEndereco.setTenant(tenant);
                    entity.setEndereco(enderecoService.findOrCreate(transientEndereco));
                }
                paciente.addEndereco(entity);
            });
        }

        // 3. Processar Identificadores
        if (request.getIdentificadores() != null) {
            // Identificar quais manter (chave: tipo + valor)
            java.util.Set<String> keysToKeep = request.getIdentificadores().stream()
                    .map(req -> req.getTipo() + "|" + req.getValor())
                    .collect(java.util.stream.Collectors.toSet());

            // Remover os que não estão na request
            paciente.getIdentificadores()
                    .removeIf(existing -> !keysToKeep.contains(existing.getTipo() + "|" + existing.getValor()));

            // Atualizar ou Adicionar
            request.getIdentificadores().forEach(req -> {
                java.util.Optional<com.upsaude.entity.paciente.PacienteIdentificador> existingOpt = paciente
                        .getIdentificadores().stream()
                        .filter(e -> e.getTipo() == req.getTipo() && e.getValor().equals(req.getValor()))
                        .findFirst();

                if (existingOpt.isPresent()) {
                    // Atualizar existente
                    var existing = existingOpt.get();
                    existing.setOrigem(req.getOrigem());
                    existing.setPrincipal(req.getPrincipal() != null ? req.getPrincipal() : false);
                    existing.setValidado(req.getValidado() != null ? req.getValidado() : false);
                    existing.setDataValidacao(req.getDataValidacao());
                    existing.setObservacoes(req.getObservacoes());
                } else {
                    // Adicionar novo
                    var entity = pacienteIdentificadorMapper.fromRequest(req);
                    entity.setPaciente(paciente);
                    entity.setTenant(tenant);
                    paciente.addIdentificador(entity);
                }
            });
        }

        // 4. Processar Contatos
        if (request.getContatos() != null) {
            // Chave: Tipo + (Telefone ou Email ou Celular)
            java.util.Set<String> keysToKeep = request.getContatos().stream()
                    .map(req -> {
                        String val = req.getTelefone() != null ? req.getTelefone()
                                : (req.getCelular() != null ? req.getCelular() : req.getEmail());
                        return req.getTipo() + "|" + val;
                    })
                    .collect(java.util.stream.Collectors.toSet());

            paciente.getContatos().removeIf(existing -> {
                String val = existing.getTelefone() != null ? existing.getTelefone()
                        : (existing.getCelular() != null ? existing.getCelular() : existing.getEmail());
                return !keysToKeep.contains(existing.getTipo() + "|" + val);
            });

            request.getContatos().forEach(req -> {
                String reqVal = req.getTelefone() != null ? req.getTelefone()
                        : (req.getCelular() != null ? req.getCelular() : req.getEmail());

                java.util.Optional<com.upsaude.entity.paciente.PacienteContato> existingOpt = paciente.getContatos()
                        .stream()
                        .filter(e -> {
                            String eVal = e.getTelefone() != null ? e.getTelefone()
                                    : (e.getCelular() != null ? e.getCelular() : e.getEmail());
                            return e.getTipo() == req.getTipo() && java.util.Objects.equals(eVal, reqVal);
                        })
                        .findFirst();

                if (existingOpt.isPresent()) {
                    var existing = existingOpt.get();
                    existing.setNome(req.getNome());
                    // outros campos se houver
                } else {
                    var entity = pacienteContatoMapper.fromRequest(req);
                    entity.setPaciente(paciente);
                    entity.setTenant(tenant);
                    paciente.addContato(entity);
                }
            });
        }

        // 5. Processar Dados Sociodemográficos
        if (request.getDadosSociodemograficos() != null) {
            var entity = dadosSociodemograficosMapper.fromRequest(request.getDadosSociodemograficos());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosSociodemograficos(entity);
        }

        // 6. Processar Dados Clínicos Básicos
        if (request.getDadosClinicosBasicos() != null) {
            var entity = dadosClinicosBasicosMapper.fromRequest(request.getDadosClinicosBasicos());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosClinicosBasicos(entity);
        }

        // 7. Processar Responsável Legal
        if (request.getResponsavelLegal() != null) {
            var entity = responsavelLegalMapper.fromRequest(request.getResponsavelLegal());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setResponsavelLegal(entity);
        }

        // 8. Processar Dados Pessoais Complementares
        if (request.getDadosPessoaisComplementares() != null) {
            var entity = dadosPessoaisComplementaresMapper.fromRequest(request.getDadosPessoaisComplementares());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosPessoaisComplementares(entity);
        }

        // 9. Processar Óbito
        if (request.getObito() != null) {
            var entity = pacienteObitoMapper.fromRequest(request.getObito());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setObito(entity);
        }

        // 10. Processar Deficiências
        if (request.getDeficiencias() != null) {
            java.util.Set<UUID> keysToKeep = request.getDeficiencias().stream()
                    .map(com.upsaude.api.request.deficiencia.DeficienciasPacienteRequest::getDeficiencia)
                    .filter(java.util.Objects::nonNull)
                    .collect(java.util.stream.Collectors.toSet());

            paciente.getDeficiencias().removeIf(existing -> existing.getDeficiencia() == null
                    || !keysToKeep.contains(existing.getDeficiencia().getId()));

            request.getDeficiencias().forEach(req -> {
                if (req.getDeficiencia() == null) {
                    return;
                }

                java.util.Optional<com.upsaude.entity.paciente.deficiencia.DeficienciasPaciente> existingOpt = paciente
                        .getDeficiencias().stream()
                        .filter(e -> e.getDeficiencia() != null
                                && e.getDeficiencia().getId().equals(req.getDeficiencia()))
                        .findFirst();

                if (existingOpt.isPresent()) {
                    var existing = existingOpt.get();
                    existing.setPossuiLaudo(req.getPossuiLaudo() != null ? req.getPossuiLaudo() : false);
                    existing.setDataDiagnostico(req.getDataDiagnostico());
                    existing.setObservacoes(req.getObservacoes());
                } else {
                    var entity = deficienciasPacienteMapper.fromRequest(req);
                    entity.setPaciente(paciente);
                    entity.setTenant(tenant);

                    com.upsaude.entity.paciente.deficiencia.Deficiencias deficiencia = deficienciasRepository
                            .findById(req.getDeficiencia())
                            .orElseThrow(() -> new com.upsaude.exception.NotFoundException(
                                    "Deficiência não encontrada com ID: " + req.getDeficiencia()));
                    entity.setDeficiencia(deficiencia);

                    paciente.getDeficiencias().add(entity);
                }
            });
        }

        // 11. Processar Vínculos Territoriais
        if (request.getVinculosTerritoriais() != null) {
            paciente.getVinculosTerritoriais().clear();
            request.getVinculosTerritoriais().forEach(req -> {
                var entity = pacienteVinculoTerritorialMapper.fromRequest(req);
                entity.setPaciente(paciente);
                entity.setTenant(tenant);
                paciente.addVinculoTerritorial(entity);
            });
        }

        // 12. Processar LGPD Consentimento
        if (request.getLgpdConsentimento() != null) {
            var entity = lgpdConsentimentoMapper.fromRequest(request.getLgpdConsentimento());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setLgpdConsentimento(entity);
        }

        log.debug("Associações processadas com sucesso para paciente ID: {}", paciente.getId());
    }
}
