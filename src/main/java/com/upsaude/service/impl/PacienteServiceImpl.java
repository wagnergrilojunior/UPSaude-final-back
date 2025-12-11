package com.upsaude.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.api.response.PacienteSimplificadoResponse;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.AlergiasPacienteService;
import com.upsaude.service.DeficienciasPacienteService;
import com.upsaude.service.DoencasPacienteService;
import com.upsaude.service.MedicacaoPacienteService;
import com.upsaude.service.PacienteService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.paciente.PacienteValidationService;
import com.upsaude.service.support.paciente.PacienteOneToOneRelationshipHandler;
import com.upsaude.service.support.paciente.PacienteAssociacoesHandler;
import com.upsaude.service.support.paciente.PacienteCalculatorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    private final AlergiasPacienteService alergiasPacienteService;
    private final DeficienciasPacienteService deficienciasPacienteService;
    private final DoencasPacienteService doencasPacienteService;
    private final MedicacaoPacienteService medicacaoPacienteService;
    private final TenantService tenantService;

    private final PacienteValidationService pacienteValidationService;
    private final PacienteOneToOneRelationshipHandler oneToOneHandler;
    private final PacienteAssociacoesHandler associacoesHandler;
    private final PacienteCalculatorService calculatorService;

    @Override
    @Transactional
    @CachePut(value = "paciente", key = "#result.id")
    public PacienteResponse criar(PacienteRequest request) {
        log.debug("Criando novo paciente: {}", request != null ? request.getNomeCompleto() : "null");

        try {
            UUID tenantIdValidado = tenantService.validarTenantAtual();
            pacienteValidationService.validarObrigatorios(request);
            pacienteValidationService.validarUnicidadeParaCriacao(request, pacienteRepository);
            pacienteValidationService.sanitizarFlags(request);

            Paciente paciente = pacienteMapper.fromRequest(request);
            paciente.setActive(true);
            if (paciente.getStatusPaciente() == null) {
                paciente.setStatusPaciente(StatusPacienteEnum.ATIVO);
            }

            oneToOneHandler.processarRelacionamentos(paciente, request);

            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            log.info("Paciente criado com sucesso. ID: {} - Será adicionado ao cache automaticamente", pacienteSalvo.getId());

            UUID tenantId = tenantIdValidado;

            associacoesHandler.processarAssociacoes(
                    request.getDoencas(),
                    doencaId -> DoencasPacienteSimplificadoRequest.builder()
                            .paciente(pacienteSalvo.getId())
                            .tenant(tenantId)
                            .doenca(doencaId)
                            .build(),
                    doencasPacienteService::criarSimplificado,
                    "Doença",
                    pacienteSalvo.getId()
            );

            associacoesHandler.processarAssociacoes(
                    request.getAlergias(),
                    alergiaId -> AlergiasPacienteSimplificadoRequest.builder()
                            .paciente(pacienteSalvo.getId())
                            .tenant(tenantId)
                            .alergia(alergiaId)
                            .build(),
                    alergiasPacienteService::criarSimplificado,
                    "Alergia",
                    pacienteSalvo.getId()
            );

            associacoesHandler.processarAssociacoes(
                    request.getDeficiencias(),
                    defId -> DeficienciasPacienteSimplificadoRequest.builder()
                            .paciente(pacienteSalvo.getId())
                            .tenant(tenantId)
                            .deficiencia(defId)
                            .build(),
                    deficienciasPacienteService::criarSimplificado,
                    "Deficiência",
                    pacienteSalvo.getId()
            );

            associacoesHandler.processarAssociacoes(
                    request.getMedicacoes(),
                    medId -> MedicacaoPacienteSimplificadoRequest.builder()
                            .paciente(pacienteSalvo.getId())
                            .tenant(tenantId)
                            .medicacao(medId)
                            .build(),
                    medicacaoPacienteService::criarSimplificado,
                    "Medicação",
                    pacienteSalvo.getId()
            );

            PacienteResponse response = pacienteMapper.toResponse(pacienteSalvo);
            if (response.getDataNascimento() != null) {
                response.setIdade(calculatorService.calcularIdade(response.getDataNascimento()));
            } else {
                response.setIdade(null);
            }
            log.debug("Paciente criado adicionado ao cache com chave: {}", response.getId());
            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Paciente. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "paciente", key = "#id")
    public PacienteResponse obterPorId(UUID id) {
        log.debug("Buscando paciente por ID: {} - Verificando cache primeiro", id);
        tenantService.validarTenantAtual();
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        log.debug("Cache MISS para paciente ID: {} - Buscando no banco de dados", id);

        Paciente paciente = pacienteRepository.findByIdCompleto(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        PacienteResponse response = pacienteMapper.toResponse(paciente);
        if (response.getDataNascimento() != null) {
            response.setIdade(calculatorService.calcularIdade(response.getDataNascimento()));
        } else {
            response.setIdade(null);
        }
        return response;
    }


    private PacienteSimplificadoResponse converterProjecaoParaSimplificado(
            com.upsaude.repository.projection.PacienteSimplificadoProjection projecao) {
        return PacienteSimplificadoResponse.builder()
                .id(projecao.getId())
                .createdAt(projecao.getCreatedAt())
                .updatedAt(projecao.getUpdatedAt())
                .active(projecao.getActive())
                .nomeCompleto(projecao.getNomeCompleto())
                .cpf(projecao.getCpf())
                .rg(projecao.getRg())
                .cns(projecao.getCns())
                .dataNascimento(projecao.getDataNascimento())
                .sexo(projecao.getSexo())
                .estadoCivil(projecao.getEstadoCivil())
                .telefone(projecao.getTelefone())
                .email(projecao.getEmail())
                .nomeMae(projecao.getNomeMae())
                .nomePai(projecao.getNomePai())
                .responsavelNome(projecao.getResponsavelNome())
                .responsavelCpf(projecao.getResponsavelCpf())
                .responsavelTelefone(projecao.getResponsavelTelefone())
                .numeroCarteirinha(projecao.getNumeroCarteirinha())
                .dataValidadeCarteirinha(projecao.getDataValidadeCarteirinha())
                .observacoes(projecao.getObservacoes())
                .racaCor(projecao.getRacaCor())
                .nacionalidade(projecao.getNacionalidade())
                .paisNascimento(projecao.getPaisNascimento())
                .naturalidade(projecao.getNaturalidade())
                .municipioNascimentoIbge(projecao.getMunicipioNascimentoIbge())
                .escolaridade(projecao.getEscolaridade())
                .ocupacaoProfissao(projecao.getOcupacaoProfissao())
                .situacaoRua(projecao.getSituacaoRua())
                .statusPaciente(projecao.getStatusPaciente())
                .dataObito(projecao.getDataObito())
                .causaObitoCid10(projecao.getCausaObitoCid10())
                .cartaoSusAtivo(projecao.getCartaoSusAtivo())
                .dataAtualizacaoCns(projecao.getDataAtualizacaoCns())
                .tipoAtendimentoPreferencial(projecao.getTipoAtendimentoPreferencial())
                .origemCadastro(projecao.getOrigemCadastro())
                .nomeSocial(projecao.getNomeSocial())
                .identidadeGenero(projecao.getIdentidadeGenero())
                .orientacaoSexual(projecao.getOrientacaoSexual())
                .possuiDeficiencia(projecao.getPossuiDeficiencia())
                .tipoDeficiencia(projecao.getTipoDeficiencia())
                .cnsValidado(projecao.getCnsValidado())
                .tipoCns(projecao.getTipoCns())
                .acompanhadoPorEquipeEsf(projecao.getAcompanhadoPorEquipeEsf())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponse> listar(Pageable pageable) {
        log.debug("Listando pacientes paginados. Página: {}, Tamanho: {}",  pageable.getPageNumber(), pageable.getPageSize());
        tenantService.validarTenantAtual();

        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);

        return pacientes.map(paciente -> {
            PacienteResponse response = pacienteMapper.toResponse(paciente);
            if (response.getDataNascimento() != null) {
                response.setIdade(calculatorService.calcularIdade(response.getDataNascimento()));
            } else {
                response.setIdade(null);
            }
            return response;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteSimplificadoResponse> listarSimplificado(Pageable pageable) {
        log.debug("Listando pacientes simplificados paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        tenantService.validarTenantAtual();

        Page<com.upsaude.repository.projection.PacienteSimplificadoProjection> projecoes =
                pacienteRepository.findAllSimplificado(pageable);

        return projecoes.map(pacienteMapper::fromProjection);
    }

    @Override
    @Transactional
    @CachePut(value = "paciente", key = "#id")
    public PacienteResponse atualizar(UUID id, PacienteRequest request) {
        log.debug("Atualizando paciente. ID: {} - Cache será atualizado automaticamente", id);

        UUID tenantIdValidado = tenantService.validarTenantAtual();
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));
        pacienteValidationService.validarObrigatorios(request);
        pacienteValidationService.validarUnicidadeParaAtualizacao(id, request, pacienteRepository);
        pacienteValidationService.sanitizarFlags(request);

        atualizarDadosPaciente(pacienteExistente, request);

        oneToOneHandler.processarRelacionamentos(pacienteExistente, request);

        Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
        log.info("Paciente atualizado com sucesso. ID: {} - Cache será atualizado automaticamente", pacienteAtualizado.getId());

        UUID tenantId = tenantIdValidado;

        associacoesHandler.processarAssociacoes(
                request.getDoencas(),
                doencaId -> DoencasPacienteSimplificadoRequest.builder()
                        .paciente(pacienteAtualizado.getId())
                        .tenant(tenantId)
                        .doenca(doencaId)
                        .build(),
                doencasPacienteService::criarSimplificado,
                "Doença",
                pacienteAtualizado.getId()
        );

        associacoesHandler.processarAssociacoes(
                request.getAlergias(),
                alergiaId -> AlergiasPacienteSimplificadoRequest.builder()
                        .paciente(pacienteAtualizado.getId())
                        .tenant(tenantId)
                        .alergia(alergiaId)
                        .build(),
                alergiasPacienteService::criarSimplificado,
                "Alergia",
                pacienteAtualizado.getId()
        );

        associacoesHandler.processarAssociacoes(
                request.getDeficiencias(),
                defId -> DeficienciasPacienteSimplificadoRequest.builder()
                        .paciente(pacienteAtualizado.getId())
                        .tenant(tenantId)
                        .deficiencia(defId)
                        .build(),
                deficienciasPacienteService::criarSimplificado,
                "Deficiência",
                pacienteAtualizado.getId()
        );

        associacoesHandler.processarAssociacoes(
                request.getMedicacoes(),
                medId -> MedicacaoPacienteSimplificadoRequest.builder()
                        .paciente(pacienteAtualizado.getId())
                        .tenant(tenantId)
                        .medicacao(medId)
                        .build(),
                medicacaoPacienteService::criarSimplificado,
                "Medicação",
                pacienteAtualizado.getId()
        );

        PacienteResponse response = pacienteMapper.toResponse(pacienteAtualizado);
        if (response.getDataNascimento() != null) {
            response.setIdade(calculatorService.calcularIdade(response.getDataNascimento()));
        } else {
            response.setIdade(null);
        }
        log.debug("Paciente atualizado no cache com chave: {}", id);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void excluir(UUID id) {
        tenantService.validarTenantAtual();
        inativar(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void inativar(UUID id) {
        log.debug("Inativando paciente. ID: {}", id);

        tenantService.validarTenantAtual();
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(paciente.getActive())) {
            throw new BadRequestException("Paciente já está inativo");
        }

        paciente.setActive(false);
        pacienteRepository.save(paciente);
        log.info("Paciente inativado com sucesso. ID: {}", id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void deletarPermanentemente(UUID id) {
        log.debug("Deletando paciente permanentemente. ID: {}", id);

        tenantService.validarTenantAtual();
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        pacienteRepository.delete(paciente);
        log.info("Paciente deletado permanentemente do banco de dados. ID: {}", id);
    }

    private void atualizarDadosPaciente(Paciente paciente, PacienteRequest request) {
        pacienteMapper.updateFromRequest(request, paciente);
    }

    

}
