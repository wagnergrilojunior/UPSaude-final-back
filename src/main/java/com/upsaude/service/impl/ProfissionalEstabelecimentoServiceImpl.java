package com.upsaude.service.impl;

import com.upsaude.api.request.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.ProfissionalEstabelecimentoResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionalEstabelecimento;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ProfissionalEstabelecimentoMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.ProfissionalEstabelecimentoRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.ProfissionalEstabelecimentoService;
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
 * Implementação do serviço de gerenciamento de Vínculos de Profissionais com Estabelecimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionalEstabelecimentoServiceImpl implements ProfissionalEstabelecimentoService {

    private final ProfissionalEstabelecimentoRepository profissionalEstabelecimentoRepository;
    private final ProfissionalEstabelecimentoMapper profissionalEstabelecimentoMapper;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    @Transactional
    @CacheEvict(value = "profissionalestabelecimento", allEntries = true)
    public ProfissionalEstabelecimentoResponse criar(ProfissionalEstabelecimentoRequest request) {
        log.debug("Criando novo vínculo de profissional com estabelecimento");

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        // Valida se já existe vínculo ativo entre o profissional e o estabelecimento
        if (profissionalEstabelecimentoRepository
                .findByProfissionalIdAndEstabelecimentoId(request.getProfissional(), request.getEstabelecimento())
                .isPresent()) {
            throw new BadRequestException("Já existe um vínculo entre este profissional e este estabelecimento");
        }

        // Valida se profissional existe
        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissional()));

        // Valida se estabelecimento existe
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimento())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento()));

        // Validar integridade multitenant: profissional e estabelecimento devem pertencer ao mesmo tenant
        if (!profissional.getTenant().getId().equals(estabelecimento.getTenant().getId())) {
            throw new BadRequestException("Profissional e estabelecimento devem pertencer ao mesmo tenant");
        }

        // Validar que profissional com registro suspenso ou inativo não pode ser vinculado
        if (profissional.getStatusRegistro() != null && 
            (profissional.getStatusRegistro() == com.upsaude.enums.StatusAtivoEnum.SUSPENSO || 
             profissional.getStatusRegistro() == com.upsaude.enums.StatusAtivoEnum.INATIVO)) {
            throw new BadRequestException("Não é possível vincular profissional com registro suspenso ou inativo");
        }

        ProfissionalEstabelecimento vinculo = profissionalEstabelecimentoMapper.fromRequest(request);
        vinculo.setProfissional(profissional);
        vinculo.setEstabelecimento(estabelecimento);
        vinculo.setActive(true);

        ProfissionalEstabelecimento vinculoSalvo = profissionalEstabelecimentoRepository.save(vinculo);
        log.info("Vínculo de profissional com estabelecimento criado com sucesso. ID: {}", vinculoSalvo.getId());

        return profissionalEstabelecimentoMapper.toResponse(vinculoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "profissionalestabelecimento", key = "#id")
    public ProfissionalEstabelecimentoResponse obterPorId(UUID id) {
        log.debug("Buscando vínculo por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

        ProfissionalEstabelecimento vinculo = profissionalEstabelecimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo não encontrado com ID: " + id));

        return profissionalEstabelecimentoMapper.toResponse(vinculo);
    }

    @Override
    public Page<ProfissionalEstabelecimentoResponse> listar(Pageable pageable) {
        log.debug("Listando vínculos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository.findAll(pageable);
        return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
    }

    @Override
    public Page<ProfissionalEstabelecimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando vínculos do profissional. Profissional ID: {}", profissionalId);

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository.findByProfissionalId(profissionalId, pageable);
        return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
    }

    @Override
    public Page<ProfissionalEstabelecimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando vínculos do estabelecimento. Estabelecimento ID: {}", estabelecimentoId);

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository.findByEstabelecimentoId(estabelecimentoId, pageable);
        return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
    }

    @Override
    public Page<ProfissionalEstabelecimentoResponse> listarPorTipoVinculo(TipoVinculoProfissionalEnum tipoVinculo, UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando vínculos por tipo. Tipo: {}, Estabelecimento ID: {}", tipoVinculo, estabelecimentoId);

        if (tipoVinculo == null) {
            throw new BadRequestException("Tipo de vínculo é obrigatório");
        }

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository
                .findByTipoVinculoAndEstabelecimentoId(tipoVinculo, estabelecimentoId, pageable);
        return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionalestabelecimento", key = "#id")
    public ProfissionalEstabelecimentoResponse atualizar(UUID id, ProfissionalEstabelecimentoRequest request) {
        log.debug("Atualizando vínculo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        ProfissionalEstabelecimento vinculoExistente = profissionalEstabelecimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo não encontrado com ID: " + id));

        atualizarDadosVinculo(vinculoExistente, request);

        ProfissionalEstabelecimento vinculoAtualizado = profissionalEstabelecimentoRepository.save(vinculoExistente);
        log.info("Vínculo atualizado com sucesso. ID: {}", vinculoAtualizado.getId());

        return profissionalEstabelecimentoMapper.toResponse(vinculoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionalestabelecimento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo vínculo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

        ProfissionalEstabelecimento vinculo = profissionalEstabelecimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(vinculo.getActive())) {
            throw new BadRequestException("Vínculo já está inativo");
        }

        vinculo.setActive(false);
        profissionalEstabelecimentoRepository.save(vinculo);
        log.info("Vínculo excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    private void atualizarDadosVinculo(ProfissionalEstabelecimento vinculo, ProfissionalEstabelecimentoRequest request) {
        ProfissionalEstabelecimento vinculoAtualizado = profissionalEstabelecimentoMapper.fromRequest(request);

        vinculo.setDataInicio(vinculoAtualizado.getDataInicio());
        vinculo.setDataFim(vinculoAtualizado.getDataFim());
        vinculo.setTipoVinculo(vinculoAtualizado.getTipoVinculo());
        vinculo.setCargaHorariaSemanal(vinculoAtualizado.getCargaHorariaSemanal());
        vinculo.setSalario(vinculoAtualizado.getSalario());
        vinculo.setNumeroMatricula(vinculoAtualizado.getNumeroMatricula());
        vinculo.setSetorDepartamento(vinculoAtualizado.getSetorDepartamento());
        vinculo.setCargoFuncao(vinculoAtualizado.getCargoFuncao());
        vinculo.setObservacoes(vinculoAtualizado.getObservacoes());
    }
}

