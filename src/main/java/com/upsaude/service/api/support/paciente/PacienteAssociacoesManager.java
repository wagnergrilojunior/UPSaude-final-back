package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteEndereco;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
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
    private final PacienteDadosPessoaisComplementaresMapper dadosPessoaisComplementaresMapper;
    private final PacienteObitoMapper pacienteObitoMapper;
    private final DeficienciasPacienteMapper deficienciasPacienteMapper;
    private final PacienteVinculoTerritorialMapper pacienteVinculoTerritorialMapper;
    private final LGPDConsentimentoMapper lgpdConsentimentoMapper;

    @Transactional
    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações para paciente ID: {}", paciente.getId());

        Tenant tenant = tenantRepository.getReferenceById(tenantId);

        if (request.getConvenio() != null) {
            convenioRepository.findById(request.getConvenio())
                    .ifPresent(paciente::setConvenio);
        }

        processarDocumentosBasicos(paciente, request, tenant);
        processarContatoBasico(paciente, request, tenant);
        processarDadosDemograficosBasicos(paciente, request, tenant);
        processarResponsavelLegalBasico(paciente, request, tenant);
        processarIntegracaoGovBasica(paciente, request, tenant);
        processarEnderecoPrincipal(paciente, request, tenant);

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

        if (request.getIdentificadores() != null) {

            java.util.Set<String> keysToKeep = request.getIdentificadores().stream()
                    .map(req -> req.getTipo() + "|" + req.getValor())
                    .collect(java.util.stream.Collectors.toSet());

            paciente.getIdentificadores()
                    .removeIf(existing -> !keysToKeep.contains(existing.getTipo() + "|" + existing.getValor()));

            request.getIdentificadores().forEach(req -> {
                java.util.Optional<com.upsaude.entity.paciente.PacienteIdentificador> existingOpt = paciente
                        .getIdentificadores().stream()
                        .filter(e -> e.getTipo() == req.getTipo() && e.getValor().equals(req.getValor()))
                        .findFirst();

                if (existingOpt.isPresent()) {

                    var existing = existingOpt.get();
                    existing.setOrigem(req.getOrigem());
                    existing.setPrincipal(req.getPrincipal() != null ? req.getPrincipal() : false);
                    existing.setValidado(req.getValidado() != null ? req.getValidado() : false);
                    existing.setDataValidacao(req.getDataValidacao());
                    existing.setObservacoes(req.getObservacoes());
                } else {

                    var entity = pacienteIdentificadorMapper.fromRequest(req);
                    entity.setPaciente(paciente);
                    entity.setTenant(tenant);
                    paciente.addIdentificador(entity);
                }
            });
        }

        if (request.getContatos() != null) {

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

                } else {
                    var entity = pacienteContatoMapper.fromRequest(req);
                    entity.setPaciente(paciente);
                    entity.setTenant(tenant);
                    paciente.addContato(entity);
                }
            });
        }

        if (request.getDadosSociodemograficos() != null) {
            var entity = dadosSociodemograficosMapper.fromRequest(request.getDadosSociodemograficos());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosSociodemograficos(entity);
        }

        if (request.getDadosClinicosBasicos() != null) {
            var entity = dadosClinicosBasicosMapper.fromRequest(request.getDadosClinicosBasicos());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosClinicosBasicos(entity);
        }

        if (request.getDadosPessoaisComplementares() != null) {
            var entity = dadosPessoaisComplementaresMapper.fromRequest(request.getDadosPessoaisComplementares());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosPessoaisComplementares(entity);
        }

        // Processar óbito apenas se o objeto obito for fornecido com dados válidos
        // (obito = true)
        // Quando obito não é fornecido (null) ou está vazio, significa obito = false e
        // deve remover o óbito existente
        if (request.getObito() != null) {
            // Verificar se o objeto obito tem pelo menos a data do óbito preenchida
            // Se todos os campos estiverem vazios/null, trata como se não tivesse sido
            // enviado
            boolean obitoVazio = request.getObito().getDataObito() == null
                    && (request.getObito().getCausaObitoCid10() == null
                            || request.getObito().getCausaObitoCid10().trim().isEmpty())
                    && request.getObito().getOrigem() == null
                    && (request.getObito().getObservacoes() == null
                            || request.getObito().getObservacoes().trim().isEmpty());

            if (obitoVazio) {
                // Se obito foi enviado vazio, remove o óbito existente se houver (obito =
                // false)
                if (paciente.getObito() != null) {
                    paciente.setObito(null);
                }
            } else {
                // Se obito foi fornecido com dados (obito = true), valida e processa
                // Validar que a data do óbito é obrigatória quando informações de óbito são
                // fornecidas
                if (request.getObito().getDataObito() == null) {
                    throw new com.upsaude.exception.BadRequestException(
                            "Data de óbito é obrigatória quando informações de óbito são fornecidas");
                }
                var entity = pacienteObitoMapper.fromRequest(request.getObito());
                entity.setPaciente(paciente);
                entity.setTenant(tenant);
                paciente.setObito(entity);
            }
        } else {
            // Se obito não foi fornecido (obito = false), remove o óbito existente se
            // houver
            if (paciente.getObito() != null) {
                paciente.setObito(null);
            }
        }

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

        if (request.getVinculosTerritoriais() != null) {
            paciente.getVinculosTerritoriais().clear();
            request.getVinculosTerritoriais().forEach(req -> {
                var entity = pacienteVinculoTerritorialMapper.fromRequest(req);
                entity.setPaciente(paciente);
                entity.setTenant(tenant);
                paciente.addVinculoTerritorial(entity);
            });
        }

        if (request.getLgpdConsentimento() != null) {
            var entity = lgpdConsentimentoMapper.fromRequest(request.getLgpdConsentimento());
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setLgpdConsentimento(entity);
        }

        log.debug("Associações processadas com sucesso para paciente ID: {}", paciente.getId());
    }

    private void processarDocumentosBasicos(Paciente paciente, PacienteRequest request, Tenant tenant) {
        if (request.getDocumentosBasicos() == null) {
            return;
        }

        var docs = request.getDocumentosBasicos();

        if (docs.getCpf() != null && !docs.getCpf().trim().isEmpty()) {
            adicionarOuAtualizarIdentificador(paciente, TipoIdentificadorEnum.CPF, docs.getCpf(), true, tenant);
        }

        if (docs.getRg() != null && !docs.getRg().trim().isEmpty()) {
            adicionarOuAtualizarIdentificador(paciente, TipoIdentificadorEnum.RG, docs.getRg(), false, tenant);
        }

        if (docs.getCns() != null && !docs.getCns().trim().isEmpty()) {
            adicionarOuAtualizarIdentificador(paciente, TipoIdentificadorEnum.CNS, docs.getCns(), false, tenant);
        }
    }

    private void adicionarOuAtualizarIdentificador(Paciente paciente, TipoIdentificadorEnum tipo, String valor,
            boolean principal, Tenant tenant) {
        var existingOpt = paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == tipo && id.getValor().equals(valor))
                .findFirst();

        if (existingOpt.isPresent()) {
            var existing = existingOpt.get();
            existing.setPrincipal(principal);
        } else {
            var entity = new com.upsaude.entity.paciente.PacienteIdentificador();
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            entity.setTipo(tipo);
            entity.setValor(valor);
            entity.setPrincipal(principal);
            paciente.addIdentificador(entity);
        }
    }

    private void processarContatoBasico(Paciente paciente, PacienteRequest request, Tenant tenant) {
        if (request.getContato() == null) {
            return;
        }

        var contato = request.getContato();

        if (contato.getTelefone() != null && !contato.getTelefone().trim().isEmpty()) {
            adicionarOuAtualizarContato(paciente, TipoContatoEnum.TELEFONE, contato.getTelefone(), null, null, tenant);
        }

        if (contato.getCelular() != null && !contato.getCelular().trim().isEmpty()) {
            adicionarOuAtualizarContato(paciente, TipoContatoEnum.WHATSAPP, null, contato.getCelular(), null, tenant);
        }

        if (contato.getEmail() != null && !contato.getEmail().trim().isEmpty()) {
            adicionarOuAtualizarContato(paciente, TipoContatoEnum.EMAIL, null, null, contato.getEmail(), tenant);
        }
    }

    private void adicionarOuAtualizarContato(Paciente paciente, TipoContatoEnum tipo, String telefone, String celular,
            String email, Tenant tenant) {
        String valor = telefone != null ? telefone : (celular != null ? celular : email);

        var existingOpt = paciente.getContatos().stream()
                .filter(c -> {
                    String cVal = c.getTelefone() != null ? c.getTelefone()
                            : (c.getCelular() != null ? c.getCelular() : c.getEmail());
                    return c.getTipo() == tipo && java.util.Objects.equals(cVal, valor);
                })
                .findFirst();

        if (existingOpt.isPresent()) {
            var existing = existingOpt.get();
            if (telefone != null)
                existing.setTelefone(telefone);
            if (celular != null)
                existing.setCelular(celular);
            if (email != null)
                existing.setEmail(email);
        } else {
            var entity = new com.upsaude.entity.paciente.PacienteContato();
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            entity.setTipo(tipo);
            entity.setTelefone(telefone);
            entity.setCelular(celular);
            entity.setEmail(email);
            paciente.addContato(entity);
        }
    }

    private void processarDadosDemograficosBasicos(Paciente paciente, PacienteRequest request, Tenant tenant) {
        if (request.getDadosDemograficos() == null) {
            return;
        }

        var demograficos = request.getDadosDemograficos();

        if (paciente.getDadosSociodemograficos() == null) {
            var entity = new com.upsaude.entity.paciente.DadosSociodemograficos();
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            entity.setSituacaoRua(false);
            paciente.setDadosSociodemograficos(entity);
        }

        var dados = paciente.getDadosSociodemograficos();
        dados.setRacaCor(demograficos.getRacaCor());
        dados.setNacionalidade(demograficos.getNacionalidade());
        dados.setPaisNascimento(demograficos.getPaisNascimento());
        dados.setNaturalidade(demograficos.getNaturalidade());
        dados.setMunicipioNascimentoIbge(demograficos.getMunicipioNascimentoIbge());
        dados.setEscolaridade(demograficos.getEscolaridade());
        dados.setOcupacaoProfissao(demograficos.getOcupacaoProfissao());
        dados.setSituacaoRua(demograficos.getSituacaoRua());

        if (paciente.getDadosPessoaisComplementares() == null) {
            var entity = new com.upsaude.entity.paciente.PacienteDadosPessoaisComplementares();
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setDadosPessoaisComplementares(entity);
        }

        var complementares = paciente.getDadosPessoaisComplementares();
        complementares.setIdentidadeGenero(demograficos.getIdentidadeGenero());
        complementares.setOrientacaoSexual(demograficos.getOrientacaoSexual());
    }

    private void processarResponsavelLegalBasico(Paciente paciente, PacienteRequest request, Tenant tenant) {
        if (request.getResponsavelLegal() == null) {
            return;
        }

        var responsavel = request.getResponsavelLegal();

        // Se todos os campos do responsável estão vazios/null, trata como se não
        // tivesse sido enviado
        boolean todosCamposVazios = (responsavel.getNome() == null || responsavel.getNome().trim().isEmpty())
                && (responsavel.getCpf() == null || responsavel.getCpf().trim().isEmpty())
                && (responsavel.getTelefone() == null || responsavel.getTelefone().trim().isEmpty());

        if (todosCamposVazios) {
            // Se o paciente já tem um responsável, remove ele
            if (paciente.getResponsavelLegal() != null) {
                paciente.setResponsavelLegal(null);
            }
            return;
        }

        if (paciente.getResponsavelLegal() == null) {
            com.upsaude.entity.paciente.ResponsavelLegal entity = new com.upsaude.entity.paciente.ResponsavelLegal();
            entity.setPaciente(paciente);
            entity.setTenant(tenant);
            paciente.setResponsavelLegal(entity);
        }

        com.upsaude.entity.paciente.ResponsavelLegal entity = paciente.getResponsavelLegal();
        entity.setNome(responsavel.getNome());
        entity.setCpf(responsavel.getCpf());
        entity.setTelefone(responsavel.getTelefone());
    }

    private void processarIntegracaoGovBasica(Paciente paciente, PacienteRequest request, Tenant tenant) {
        if (request.getIntegracaoGov() == null) {
            return;
        }

        var integracao = request.getIntegracaoGov();

        IntegracaoGov integracaoGov = paciente.getIntegracoesGov() != null && !paciente.getIntegracoesGov().isEmpty()
                ? paciente.getIntegracoesGov().get(0)
                : new IntegracaoGov();

        integracaoGov.setPaciente(paciente);
        integracaoGov.setTenant(tenant);
        integracaoGov.setCartaoSusAtivo(integracao.getCartaoSusAtivo());
        integracaoGov.setDataAtualizacaoCns(integracao.getDataAtualizacaoCns());
        integracaoGov.setOrigemCadastro(integracao.getOrigemCadastro());
        integracaoGov.setCnsValidado(integracao.getCnsValidado());
        integracaoGov.setTipoCns(integracao.getTipoCns());

        if (paciente.getIntegracoesGov() == null || paciente.getIntegracoesGov().isEmpty()) {
            paciente.setIntegracoesGov(new java.util.ArrayList<>());
            paciente.getIntegracoesGov().add(integracaoGov);
        }
    }

    private void processarEnderecoPrincipal(Paciente paciente, PacienteRequest request, Tenant tenant) {
        if (request.getEnderecoPrincipal() == null) {
            return;
        }

        Endereco transientEndereco = enderecoMapper.fromRequest(request.getEnderecoPrincipal());
        transientEndereco.setTenant(tenant);
        Endereco endereco = enderecoService.findOrCreate(transientEndereco);

        PacienteEndereco pacienteEndereco = paciente.getEnderecos().stream()
                .filter(pe -> pe.getEndereco() != null && pe.getEndereco().getId().equals(endereco.getId()))
                .findFirst()
                .orElse(null);

        if (pacienteEndereco == null) {
            pacienteEndereco = new PacienteEndereco();
            pacienteEndereco.setPaciente(paciente);
            pacienteEndereco.setTenant(tenant);
            pacienteEndereco.setEndereco(endereco);
            paciente.addEndereco(pacienteEndereco);
        }
    }
}
