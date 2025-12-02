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
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AtendimentoMapper;
import com.upsaude.repository.AtendimentoRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.EquipeSaudeRepository;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.service.AtendimentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        log.debug("Criando novo atendimento para paciente: {}", request.getPacienteId());

        validarDadosBasicos(request);

        Atendimento atendimento = atendimentoMapper.fromRequest(request);

        // Carrega e define o estabelecimento
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
        atendimento.setEstabelecimento(estabelecimento);

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        atendimento.setPaciente(paciente);

        // Carrega e define o profissional
        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalId()));
        atendimento.setProfissional(profissional);

        // Especialidade é opcional
        if (request.getEspecialidadeId() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidadeId())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidadeId()));
            atendimento.setEspecialidade(especialidade);
        }

        // Equipe de saúde é opcional
        if (request.getEquipeSaudeId() != null) {
            EquipeSaude equipeSaude = equipeSaudeRepository.findById(request.getEquipeSaudeId())
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + request.getEquipeSaudeId()));
            atendimento.setEquipeSaude(equipeSaude);
        }

        // Convênio é opcional
        if (request.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenioId())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenioId()));
            atendimento.setConvenio(convenio);
        }

        // CID principal é opcional
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            atendimento.setCidPrincipal(cidPrincipal);
        }

        atendimento.setActive(true);

        Atendimento atendimentoSalvo = atendimentoRepository.save(atendimento);
        log.info("Atendimento criado com sucesso. ID: {}", atendimentoSalvo.getId());

        return atendimentoMapper.toResponse(atendimentoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "atendimento", key = "#id")
    public AtendimentoResponse obterPorId(UUID id) {
        log.debug("Buscando atendimento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

        return atendimentoMapper.toResponse(atendimento);
    }

    @Override
    public Page<AtendimentoResponse> listar(Pageable pageable) {
        log.debug("Listando atendimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Atendimento> atendimentos = atendimentoRepository.findAll(pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    public Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando atendimentos do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<Atendimento> atendimentos = atendimentoRepository.findByPacienteIdOrderByInformacoesDataHoraDesc(pacienteId, pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    public Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando atendimentos do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        Page<Atendimento> atendimentos = atendimentoRepository.findByProfissionalIdOrderByInformacoesDataHoraDesc(profissionalId, pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    public Page<AtendimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando atendimentos do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Atendimento> atendimentos = atendimentoRepository.findByEstabelecimentoIdOrderByInformacoesDataHoraDesc(estabelecimentoId, pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "atendimento", key = "#id")
    public AtendimentoResponse atualizar(UUID id, AtendimentoRequest request) {
        log.debug("Atualizando atendimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        validarDadosBasicos(request);

        Atendimento atendimentoExistente = atendimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

        atualizarDadosAtendimento(atendimentoExistente, request);

        Atendimento atendimentoAtualizado = atendimentoRepository.save(atendimentoExistente);
        log.info("Atendimento atualizado com sucesso. ID: {}", atendimentoAtualizado.getId());

        return atendimentoMapper.toResponse(atendimentoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "atendimento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo atendimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(atendimento.getActive())) {
            throw new BadRequestException("Atendimento já está inativo");
        }

        atendimento.setActive(false);
        atendimentoRepository.save(atendimento);
        log.info("Atendimento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(AtendimentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do atendimento são obrigatórios");
        }
        if (request.getEstabelecimentoId() == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getProfissionalId() == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }
        if (request.getInformacoes() == null || request.getInformacoes().getDataHora() == null) {
            throw new BadRequestException("Data e hora do atendimento são obrigatórias");
        }
    }

    private void atualizarDadosAtendimento(Atendimento atendimento, AtendimentoRequest request) {
        // Atualiza informações básicas
        if (request.getInformacoes() != null) {
            atendimento.setInformacoes(request.getInformacoes());
        }

        // Atualiza anamnese
        if (request.getAnamnese() != null) {
            atendimento.setAnamnese(request.getAnamnese());
        }

        // Atualiza diagnóstico
        if (request.getDiagnostico() != null) {
            atendimento.setDiagnostico(request.getDiagnostico());
        }

        // Atualiza procedimentos realizados
        if (request.getProcedimentosRealizados() != null) {
            atendimento.setProcedimentosRealizados(request.getProcedimentosRealizados());
        }

        // Atualiza classificação de risco
        if (request.getClassificacaoRisco() != null) {
            atendimento.setClassificacaoRisco(request.getClassificacaoRisco());
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
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            atendimento.setEstabelecimento(estabelecimento);
        }

        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            atendimento.setPaciente(paciente);
        }

        if (request.getProfissionalId() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalId()));
            atendimento.setProfissional(profissional);
        }

        // Especialidade é opcional
        if (request.getEspecialidadeId() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidadeId())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidadeId()));
            atendimento.setEspecialidade(especialidade);
        } else {
            atendimento.setEspecialidade(null);
        }

        // Equipe de saúde é opcional
        if (request.getEquipeSaudeId() != null) {
            EquipeSaude equipeSaude = equipeSaudeRepository.findById(request.getEquipeSaudeId())
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + request.getEquipeSaudeId()));
            atendimento.setEquipeSaude(equipeSaude);
        } else {
            atendimento.setEquipeSaude(null);
        }

        // Convênio é opcional
        if (request.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenioId())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenioId()));
            atendimento.setConvenio(convenio);
        } else {
            atendimento.setConvenio(null);
        }

        // CID principal é opcional
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            atendimento.setCidPrincipal(cidPrincipal);
        } else {
            atendimento.setCidPrincipal(null);
        }
    }
}
