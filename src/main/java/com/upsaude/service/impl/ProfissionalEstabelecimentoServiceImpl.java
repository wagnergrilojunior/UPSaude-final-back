package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ProfissionalEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.ProfissionalEstabelecimento;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.estabelecimento.ProfissionalEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.ProfissionalEstabelecimentoRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.service.estabelecimento.ProfissionalEstabelecimentoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

        if (profissionalEstabelecimentoRepository
                .findByProfissionalIdAndEstabelecimentoId(request.getProfissional(), request.getEstabelecimento())
                .isPresent()) {
            throw new BadRequestException("Já existe um vínculo entre este profissional e este estabelecimento");
        }

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissional()));

        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimento())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento()));

        if (!profissional.getTenant().getId().equals(estabelecimento.getTenant().getId())) {
            throw new BadRequestException("Profissional e estabelecimento devem pertencer ao mesmo tenant");
        }

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
        Pageable safePageable = Objects.requireNonNull(pageable, "pageable");
        log.debug("Listando vínculos paginados. Página: {}, Tamanho: {}",
                safePageable.getPageNumber(), safePageable.getPageSize());

        try {
            Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository.findAll(safePageable);
            return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
        } catch (Exception ex) {
            if (isMissingProfissionaisEstabelecimentosTable(ex)) {
                log.warn("Tabela public.profissionais_estabelecimentos não existe no banco atual; retornando página vazia para listagem.");
                return Page.empty(safePageable);
            }
            throw ex;
        }
    }

    @Override
    public Page<ProfissionalEstabelecimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando vínculos do profissional. Profissional ID: {}", profissionalId);

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        try {
            Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository.findByProfissionalId(profissionalId, pageable);
            return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
        } catch (Exception ex) {
            if (isMissingProfissionaisEstabelecimentosTable(ex)) {
                log.warn("Tabela public.profissionais_estabelecimentos não existe no banco atual; retornando página vazia para listagem por profissional.");
                return Page.empty(pageable);
            }
            throw ex;
        }
    }

    @Override
    public Page<ProfissionalEstabelecimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando vínculos do estabelecimento. Estabelecimento ID: {}", estabelecimentoId);

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository.findByEstabelecimentoId(estabelecimentoId, pageable);
            return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
        } catch (Exception ex) {
            if (isMissingProfissionaisEstabelecimentosTable(ex)) {
                log.warn("Tabela public.profissionais_estabelecimentos não existe no banco atual; retornando página vazia para listagem por estabelecimento.");
                return Page.empty(pageable);
            }
            throw ex;
        }
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

        try {
            Page<ProfissionalEstabelecimento> vinculos = profissionalEstabelecimentoRepository
                    .findByTipoVinculoAndEstabelecimentoId(tipoVinculo, estabelecimentoId, pageable);
            return vinculos.map(profissionalEstabelecimentoMapper::toResponse);
        } catch (Exception ex) {
            if (isMissingProfissionaisEstabelecimentosTable(ex)) {
                log.warn("Tabela public.profissionais_estabelecimentos não existe no banco atual; retornando página vazia para listagem por tipo de vínculo.");
                return Page.empty(pageable);
            }
            throw ex;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionalestabelecimento", key = "#id")
    public ProfissionalEstabelecimentoResponse atualizar(UUID id, ProfissionalEstabelecimentoRequest request) {
        log.debug("Atualizando vínculo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

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

    private boolean isMissingProfissionaisEstabelecimentosTable(Throwable ex) {
        Throwable current = ex;
        while (current != null) {
            String msg = current.getMessage();
            if (msg != null && msg.contains("relation \"public.profissionais_estabelecimentos\" does not exist")) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
