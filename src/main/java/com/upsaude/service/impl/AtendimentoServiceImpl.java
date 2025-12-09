package com.upsaude.service.impl;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.api.response.AtendimentoResponse;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Convenio;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AtendimentoMapper;
import com.upsaude.mapper.embeddable.InformacoesAtendimentoMapper;
import com.upsaude.mapper.embeddable.AnamneseAtendimentoMapper;
import com.upsaude.mapper.embeddable.DiagnosticoAtendimentoMapper;
import com.upsaude.mapper.embeddable.ProcedimentosRealizadosAtendimentoMapper;
import com.upsaude.mapper.embeddable.ClassificacaoRiscoAtendimentoMapper;
import com.upsaude.repository.AtendimentoRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.EquipeSaudeRepository;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Atendimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final AtendimentoMapper atendimentoMapper;
    private final InformacoesAtendimentoMapper informacoesAtendimentoMapper;
    private final AnamneseAtendimentoMapper anamneseAtendimentoMapper;
    private final DiagnosticoAtendimentoMapper diagnosticoAtendimentoMapper;
    private final ProcedimentosRealizadosAtendimentoMapper procedimentosRealizadosAtendimentoMapper;
    private final ClassificacaoRiscoAtendimentoMapper classificacaoRiscoAtendimentoMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final CidDoencasRepository cidDoencasRepository;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EquipeSaudeRepository equipeSaudeRepository;
    private final ConvenioRepository convenioRepository;

    @Override
    @Transactional
    @CacheEvict(value = "atendimento", allEntries = true)
    public AtendimentoResponse criar(AtendimentoRequest request) {
        log.debug("Criando novo atendimento. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar atendimento com request nulo");
            throw new BadRequestException("Dados do atendimento são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Atendimento atendimento = atendimentoMapper.fromRequest(request);

            // Carrega e define o paciente
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            atendimento.setPaciente(paciente);

            // Carrega e define o profissional
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissional()));
            atendimento.setProfissional(profissional);

            // Especialidade é opcional
            if (request.getEspecialidade() != null) {
                EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidade())
                        .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidade()));
                atendimento.setEspecialidade(especialidade);
            }

            // Equipe de saúde é opcional
            if (request.getEquipeSaude() != null) {
                EquipeSaude equipeSaude = equipeSaudeRepository.findById(request.getEquipeSaude())
                        .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + request.getEquipeSaude()));
                atendimento.setEquipeSaude(equipeSaude);
            }

            // Convênio é opcional
            if (request.getConvenio() != null) {
                Convenio convenio = convenioRepository.findById(request.getConvenio())
                        .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
                atendimento.setConvenio(convenio);
            }

            // CID principal é opcional
            if (request.getCidPrincipal() != null) {
                CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                        .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
                atendimento.setCidPrincipal(cidPrincipal);
            }

            atendimento.setActive(true);

            Atendimento atendimentoSalvo = atendimentoRepository.save(atendimento);
            log.info("Atendimento criado com sucesso. ID: {}", atendimentoSalvo.getId());

            return atendimentoMapper.toResponse(atendimentoSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar atendimento. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar atendimento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir atendimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar atendimento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "atendimento", key = "#id")
    public AtendimentoResponse obterPorId(UUID id) {
        log.debug("Buscando atendimento por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de atendimento");
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        try {
            Atendimento atendimento = atendimentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

            log.debug("Atendimento encontrado. ID: {}", id);
            return atendimentoMapper.toResponse(atendimento);
        } catch (NotFoundException e) {
            log.warn("Atendimento não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar atendimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar atendimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar atendimento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listar(Pageable pageable) {
        log.debug("Listando atendimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Atendimento> atendimentos = atendimentoRepository.findAll(pageable);
            log.debug("Listagem de atendimentos concluída. Total de elementos: {}", atendimentos.getTotalElements());
            return atendimentos.map(atendimentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar atendimentos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar atendimentos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar atendimentos. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando atendimentos do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            Page<Atendimento> atendimentos = atendimentoRepository.findByPacienteIdOrderByInformacoesDataHoraDesc(pacienteId, pageable);
            log.debug("Listagem de atendimentos do paciente concluída. Paciente ID: {}, Total: {}", pacienteId, atendimentos.getTotalElements());
            return atendimentos.map(atendimentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar atendimentos por paciente. Paciente ID: {}, Pageable: {}", pacienteId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar atendimentos do paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar atendimentos por paciente. Paciente ID: {}, Pageable: {}", pacienteId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando atendimentos do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        try {
            Page<Atendimento> atendimentos = atendimentoRepository.findByProfissionalIdOrderByInformacoesDataHoraDesc(profissionalId, pageable);
            log.debug("Listagem de atendimentos do profissional concluída. Profissional ID: {}, Total: {}", profissionalId, atendimentos.getTotalElements());
            return atendimentos.map(atendimentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar atendimentos por profissional. Profissional ID: {}, Pageable: {}", profissionalId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar atendimentos do profissional", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar atendimentos por profissional. Profissional ID: {}, Pageable: {}", profissionalId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando atendimentos do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<Atendimento> atendimentos = atendimentoRepository.findByEstabelecimentoIdOrderByInformacoesDataHoraDesc(estabelecimentoId, pageable);
            log.debug("Listagem de atendimentos do estabelecimento concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, atendimentos.getTotalElements());
            return atendimentos.map(atendimentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar atendimentos por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar atendimentos do estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar atendimentos por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "atendimento", key = "#id")
    public AtendimentoResponse atualizar(UUID id, AtendimentoRequest request) {
        log.debug("Atualizando atendimento. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de atendimento");
            throw new BadRequestException("ID do atendimento é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de atendimento. ID: {}", id);
            throw new BadRequestException("Dados do atendimento são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Atendimento atendimentoExistente = atendimentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

            atualizarDadosAtendimento(atendimentoExistente, request);

            Atendimento atendimentoAtualizado = atendimentoRepository.save(atendimentoExistente);
            log.info("Atendimento atualizado com sucesso. ID: {}", atendimentoAtualizado.getId());

            return atendimentoMapper.toResponse(atendimentoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar atendimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar atendimento. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar atendimento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar atendimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar atendimento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "atendimento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo atendimento. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de atendimento");
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        try {
            Atendimento atendimento = atendimentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(atendimento.getActive())) {
                log.warn("Tentativa de excluir atendimento já inativo. ID: {}", id);
                throw new BadRequestException("Atendimento já está inativo");
            }

            atendimento.setActive(false);
            atendimentoRepository.save(atendimento);
            log.info("Atendimento excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir atendimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir atendimento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir atendimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir atendimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir atendimento. ID: {}", id, e);
            throw e;
        }
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    private void atualizarDadosAtendimento(Atendimento atendimento, AtendimentoRequest request) {
        // Atualiza informações básicas usando mapper
        if (request.getInformacoes() != null) {
            if (atendimento.getInformacoes() == null) {
                atendimento.setInformacoes(informacoesAtendimentoMapper.toEntity(request.getInformacoes()));
            } else {
                informacoesAtendimentoMapper.updateFromRequest(request.getInformacoes(), atendimento.getInformacoes());
            }
        }

        // Atualiza anamnese usando mapper
        if (request.getAnamnese() != null) {
            if (atendimento.getAnamnese() == null) {
                atendimento.setAnamnese(anamneseAtendimentoMapper.toEntity(request.getAnamnese()));
            } else {
                anamneseAtendimentoMapper.updateFromRequest(request.getAnamnese(), atendimento.getAnamnese());
            }
        }

        // Atualiza diagnóstico usando mapper
        if (request.getDiagnostico() != null) {
            if (atendimento.getDiagnostico() == null) {
                atendimento.setDiagnostico(diagnosticoAtendimentoMapper.toEntity(request.getDiagnostico()));
            } else {
                diagnosticoAtendimentoMapper.updateFromRequest(request.getDiagnostico(), atendimento.getDiagnostico());
            }
        }

        // Atualiza procedimentos realizados usando mapper
        if (request.getProcedimentosRealizados() != null) {
            if (atendimento.getProcedimentosRealizados() == null) {
                atendimento.setProcedimentosRealizados(procedimentosRealizadosAtendimentoMapper.toEntity(request.getProcedimentosRealizados()));
            } else {
                procedimentosRealizadosAtendimentoMapper.updateFromRequest(request.getProcedimentosRealizados(), atendimento.getProcedimentosRealizados());
            }
        }

        // Atualiza classificação de risco usando mapper
        if (request.getClassificacaoRisco() != null) {
            if (atendimento.getClassificacaoRisco() == null) {
                atendimento.setClassificacaoRisco(classificacaoRiscoAtendimentoMapper.toEntity(request.getClassificacaoRisco()));
            } else {
                classificacaoRiscoAtendimentoMapper.updateFromRequest(request.getClassificacaoRisco(), atendimento.getClassificacaoRisco());
            }
        }

        // Atualiza anotações
        if (request.getAnotacoes() != null) {
            atendimento.setAnotacoes(request.getAnotacoes());
        }

        // Atualiza observações internas
        if (request.getObservacoesInternas() != null) {
            atendimento.setObservacoesInternas(request.getObservacoesInternas());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            atendimento.setPaciente(paciente);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissional()));
            atendimento.setProfissional(profissional);
        }

        // Especialidade é opcional
        if (request.getEspecialidade() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidade())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidade()));
            atendimento.setEspecialidade(especialidade);
        } else {
            atendimento.setEspecialidade(null);
        }

        // Equipe de saúde é opcional
        if (request.getEquipeSaude() != null) {
            EquipeSaude equipeSaude = equipeSaudeRepository.findById(request.getEquipeSaude())
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + request.getEquipeSaude()));
            atendimento.setEquipeSaude(equipeSaude);
        } else {
            atendimento.setEquipeSaude(null);
        }

        // Convênio é opcional
        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            atendimento.setConvenio(convenio);
        } else {
            atendimento.setConvenio(null);
        }

        // CID principal é opcional
        if (request.getCidPrincipal() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
            atendimento.setCidPrincipal(cidPrincipal);
        } else {
            atendimento.setCidPrincipal(null);
        }
    }
}
