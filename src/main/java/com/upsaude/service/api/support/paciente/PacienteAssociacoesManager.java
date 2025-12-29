package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.deficiencia.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.entity.paciente.PacienteDadosPessoaisComplementares;
import com.upsaude.entity.paciente.PacienteEndereco;
import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.OrigemIdentificadorEnum;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.paciente.PacienteDadosPessoaisComplementaresRepository;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import com.upsaude.service.api.deficiencia.DeficienciasPacienteService;
import com.upsaude.service.api.geral.EnderecoService;
import com.upsaude.service.api.paciente.PacienteContatoService;
import com.upsaude.service.api.paciente.PacienteIdentificadorService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteAssociacoesManager {

    private final PacienteAssociacoesHandler associacoesHandler;
    private final DeficienciasPacienteService deficienciasPacienteService;
    private final PacienteIdentificadorService identificadorService;
    private final PacienteContatoService contatoService;
    private final PacienteDadosPessoaisComplementaresRepository dadosPessoaisComplementaresRepository;
    private final TenantService tenantService;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    @Transactional
    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações para paciente ID: {}", paciente.getId());

        // Processar identificadores (CPF, CNS, RG)
        processarIdentificadores(paciente, request);
        
        // Processar contatos (telefone, email)
        processarContatos(paciente, request);
        
        // Processar dados pessoais complementares (nomeMae, nomePai, identidadeGenero, orientacaoSexual)
        processarDadosPessoaisComplementares(paciente, request);
        
        // Processar endereços
        processarEnderecos(paciente, request, tenantId);
        
        // Processar deficiências (associação N:N)
        processarDeficiencias(paciente, request, tenantId);

        log.info("Todas as associações processadas para paciente ID: {}", paciente.getId());
    }

    private void processarIdentificadores(Paciente paciente, PacienteRequest request) {
        log.debug("Processando identificadores para paciente ID: {}", paciente.getId());

        // CPF (obrigatório)
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            criarIdentificador(paciente, TipoIdentificadorEnum.CPF, request.getCpf(), 
                    request.getCnsValidado() != null && request.getCnsValidado(), true);
        }

        // CNS (opcional)
        if (request.getCns() != null && !request.getCns().trim().isEmpty()) {
            criarIdentificador(paciente, TipoIdentificadorEnum.CNS, request.getCns(), 
                    request.getCnsValidado() != null && request.getCnsValidado(), false);
        }

        // RG (opcional)
        if (request.getRg() != null && !request.getRg().trim().isEmpty()) {
            criarIdentificador(paciente, TipoIdentificadorEnum.RG, request.getRg(), false, false);
        }
    }

    private void criarIdentificador(Paciente paciente, TipoIdentificadorEnum tipo, String valor, 
                                    Boolean validado, Boolean principal) {
        try {
            PacienteIdentificador identificador = new PacienteIdentificador();
            identificador.setPaciente(paciente);
            identificador.setTipo(tipo);
            identificador.setValor(valor.trim());
            identificador.setOrigem(OrigemIdentificadorEnum.UPSAUDE);
            identificador.setValidado(validado != null ? validado : false);
            identificador.setPrincipal(principal != null ? principal : false);
            identificador.setActive(true);
            
            identificadorService.criar(identificador);
            log.debug("Identificador {} criado para paciente ID: {}", tipo.getDescricao(), paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao criar identificador {} para paciente ID: {}. Erro: {}", 
                    tipo.getDescricao(), paciente.getId(), e.getMessage());
            // Não lança exceção para não bloquear a criação do paciente
        }
    }

    private void processarContatos(Paciente paciente, PacienteRequest request) {
        log.debug("Processando contatos para paciente ID: {}", paciente.getId());

        // Verificar se há dados de contato para criar
        boolean temContato = (request.getTelefone() != null && !request.getTelefone().trim().isEmpty()) ||
                             (request.getEmail() != null && !request.getEmail().trim().isEmpty()) ||
                             (request.getResponsavelTelefone() != null && !request.getResponsavelTelefone().trim().isEmpty());

        if (!temContato) {
            return;
        }

        try {
            PacienteContato contato = new PacienteContato();
            contato.setPaciente(paciente);
            contato.setTipo(TipoContatoEnum.TELEFONE); // Tipo padrão
            
            // Preencher campos de contato
            if (request.getTelefone() != null && !request.getTelefone().trim().isEmpty()) {
                contato.setTelefone(request.getTelefone().trim());
            }
            
            if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                contato.setEmail(request.getEmail().trim());
                contato.setTipo(TipoContatoEnum.EMAIL);
            }
            
            if (request.getResponsavelTelefone() != null && !request.getResponsavelTelefone().trim().isEmpty()) {
                if (contato.getTelefone() == null) {
                    contato.setTelefone(request.getResponsavelTelefone().trim());
                } else {
                    // Se já tem telefone, criar outro contato para o responsável
                    PacienteContato contatoResponsavel = new PacienteContato();
                    contatoResponsavel.setPaciente(paciente);
                    contatoResponsavel.setTipo(TipoContatoEnum.TELEFONE);
                    contatoResponsavel.setTelefone(request.getResponsavelTelefone().trim());
                    contatoResponsavel.setActive(true);
                    contatoService.criar(contatoResponsavel);
                }
            }
            
            contato.setActive(true);
            contatoService.criar(contato);
            log.debug("Contato criado para paciente ID: {}", paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao criar contato para paciente ID: {}. Erro: {}", 
                    paciente.getId(), e.getMessage());
            // Não lança exceção para não bloquear a criação do paciente
        }
    }

    private void processarDadosPessoaisComplementares(Paciente paciente, PacienteRequest request) {
        // Verifica se há dados para criar
        boolean temDados = (request.getNomeMae() != null && !request.getNomeMae().trim().isEmpty()) ||
                           (request.getNomePai() != null && !request.getNomePai().trim().isEmpty()) ||
                           request.getIdentidadeGenero() != null ||
                           request.getOrientacaoSexual() != null;

        if (!temDados) {
            return;
        }

        log.debug("Processando dados pessoais complementares para paciente ID: {}", paciente.getId());

        try {
            // Verifica se já existe registro
            PacienteDadosPessoaisComplementares dados = dadosPessoaisComplementaresRepository
                    .findByPacienteId(paciente.getId())
                    .orElse(new PacienteDadosPessoaisComplementares());

            dados.setPaciente(paciente);
            
            if (request.getNomeMae() != null && !request.getNomeMae().trim().isEmpty()) {
                dados.setNomeMae(request.getNomeMae().trim());
            }
            
            if (request.getNomePai() != null && !request.getNomePai().trim().isEmpty()) {
                dados.setNomePai(request.getNomePai().trim());
            }
            
            if (request.getIdentidadeGenero() != null) {
                dados.setIdentidadeGenero(request.getIdentidadeGenero());
            }
            
            if (request.getOrientacaoSexual() != null) {
                dados.setOrientacaoSexual(request.getOrientacaoSexual());
            }

            dados.setActive(true);
            
            // Define tenant se não tiver
            if (dados.getTenant() == null) {
                dados.setTenant(tenantService.obterTenantDoUsuarioAutenticado());
            }

            dadosPessoaisComplementaresRepository.save(dados);
            paciente.setDadosPessoaisComplementares(dados);
            
            log.debug("Dados pessoais complementares criados/atualizados para paciente ID: {}", paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao criar dados pessoais complementares para paciente ID: {}. Erro: {}", 
                    paciente.getId(), e.getMessage());
            // Não lança exceção para não bloquear a criação do paciente
        }
    }

    private void processarEnderecos(Paciente paciente, PacienteRequest request, UUID tenantId) {
        if (request.getEnderecos() == null || request.getEnderecos().isEmpty()) {
            log.debug("Nenhum endereço para processar para paciente ID: {}", paciente.getId());
            return;
        }

        log.debug("Processando {} endereço(s) para paciente ID: {}", request.getEnderecos().size(), paciente.getId());

        try {
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                log.warn("Não foi possível obter tenant do usuário autenticado para processar endereços");
                return;
            }

            boolean primeiroEndereco = true;
            for (com.upsaude.api.request.geral.EnderecoRequest enderecoRequest : request.getEnderecos()) {
                try {
                    Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
                    endereco.setActive(true);
                    endereco.setTenant(tenant);

                    if (endereco.getSemNumero() == null) {
                        endereco.setSemNumero(false);
                    }

                    // Processar estado e cidade
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

                    // Usar findOrCreate para evitar duplicação
                    Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);

                    // Criar vínculo PacienteEndereco
                    PacienteEndereco pacienteEndereco = new PacienteEndereco();
                    pacienteEndereco.setPaciente(paciente);
                    pacienteEndereco.setEndereco(enderecoProcessado);
                    pacienteEndereco.setPrincipal(primeiroEndereco); // Primeiro endereço é principal
                    pacienteEndereco.setActive(true);
                    pacienteEndereco.setTenant(tenant);

                    // Adicionar ao paciente (cascade salvará automaticamente)
                    paciente.addEndereco(pacienteEndereco);

                    primeiroEndereco = false;
                    log.debug("Endereço processado e vinculado ao paciente ID: {}. Endereço ID: {}", 
                            paciente.getId(), enderecoProcessado.getId());
                } catch (Exception e) {
                    log.warn("Erro ao processar endereço para paciente ID: {}. Erro: {}", 
                            paciente.getId(), e.getMessage());
                    // Continua processando outros endereços
                }
            }

            log.info("Endereços processados para paciente ID: {}", paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao processar endereços para paciente ID: {}. Erro: {}", 
                    paciente.getId(), e.getMessage());
            // Não lança exceção para não bloquear a criação do paciente
        }
    }

    private void processarDeficiencias(Paciente paciente, PacienteRequest request, UUID tenantId) {
        associacoesHandler.processarAssociacoes(
                request.getDeficiencias(),
                defId -> DeficienciasPacienteSimplificadoRequest.builder()
                        .paciente(paciente.getId())
                        .tenant(tenantId)
                        .deficiencia(defId)
                        .build(),
                deficienciasPacienteService::criarSimplificado,
                "Deficiência",
                paciente.getId()
        );
    }
}
