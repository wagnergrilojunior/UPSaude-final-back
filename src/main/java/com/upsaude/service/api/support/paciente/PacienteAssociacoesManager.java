package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.deficiencia.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.entity.paciente.PacienteDadosPessoaisComplementares;
import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.enums.OrigemIdentificadorEnum;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.repository.paciente.PacienteDadosPessoaisComplementaresRepository;
import com.upsaude.service.api.deficiencia.DeficienciasPacienteService;
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

    @Transactional
    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações para paciente ID: {}", paciente.getId());

        // Processar identificadores (CPF, CNS, RG)
        processarIdentificadores(paciente, request);
        
        // Processar contatos (telefone, email)
        processarContatos(paciente, request);
        
        // Processar dados pessoais complementares (nomeMae, nomePai, identidadeGenero, orientacaoSexual)
        processarDadosPessoaisComplementares(paciente, request);
        
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

        // Telefone (opcional)
        if (request.getTelefone() != null && !request.getTelefone().trim().isEmpty()) {
            criarContato(paciente, TipoContatoEnum.TELEFONE, request.getTelefone(), true);
        }

        // Email (opcional)
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            criarContato(paciente, TipoContatoEnum.EMAIL, request.getEmail(), true);
        }

        // Telefone do responsável (opcional, não principal)
        if (request.getResponsavelTelefone() != null && !request.getResponsavelTelefone().trim().isEmpty()) {
            criarContato(paciente, TipoContatoEnum.TELEFONE, request.getResponsavelTelefone(), false);
        }
    }

    private void criarContato(Paciente paciente, TipoContatoEnum tipo, String valor, Boolean principal) {
        try {
            PacienteContato contato = new PacienteContato();
            contato.setPaciente(paciente);
            contato.setTipo(tipo);
            contato.setValor(valor.trim());
            contato.setPrincipal(principal != null ? principal : false);
            contato.setVerificado(false);
            contato.setActive(true);
            
            contatoService.criar(contato);
            log.debug("Contato {} criado para paciente ID: {}", tipo.getDescricao(), paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao criar contato {} para paciente ID: {}. Erro: {}", 
                    tipo.getDescricao(), paciente.getId(), e.getMessage());
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
