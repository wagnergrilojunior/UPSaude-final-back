package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.embeddable.ContraindicacoesPrecaucoesMedicamento;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.entity.embeddable.IdentificacaoMedicamento;
import com.upsaude.entity.embeddable.DosagemAdministracaoMedicamento;
import com.upsaude.entity.embeddable.RegistroControleMedicamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacaoMapper;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.repository.FabricantesMedicamentoRepository;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.service.MedicacaoService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoServiceImpl implements MedicacaoService {

    private final MedicacaoRepository medicacaoRepository;
    private final MedicacaoMapper medicacaoMapper;
    private final FabricantesMedicamentoRepository fabricantesMedicamentoRepository;

    @Override
    @Transactional
    @CacheEvict(value = "medicacao", allEntries = true)
    public MedicacaoResponse criar(MedicacaoRequest request) {
        log.debug("Criando nova medicação. Request: {}", request);

        if (request == null) {
            log.warn("Tentativa de criar medicação com request nulo");
            throw new BadRequestException("Dados da medicação são obrigatórios");
        }

        try {

            Medicacao medicacao = medicacaoMapper.fromRequest(request);
            medicacao.setActive(true);

            if (request.getFabricanteEntity() != null) {
                FabricantesMedicamento fabricante = fabricantesMedicamentoRepository.findById(request.getFabricanteEntity())
                        .orElseThrow(() -> new NotFoundException("Fabricante não encontrado com ID: " + request.getFabricanteEntity()));
                medicacao.setFabricanteEntity(fabricante);
            }

            garantirValoresPadrao(medicacao);

            Medicacao medicacaoSalvo = medicacaoRepository.save(medicacao);
            log.info("Medicação criada com sucesso. ID: {}", medicacaoSalvo.getId());

            return medicacaoMapper.toResponse(medicacaoSalvo);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar medicação. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar medicação. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar medicação. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "medicacao", key = "#id")
    public MedicacaoResponse obterPorId(UUID id) {
        log.debug("Buscando medicação por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de medicação");
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        try {
            Medicacao medicacao = medicacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

            log.debug("Medicação encontrada. ID: {}", id);
            return medicacaoMapper.toResponse(medicacao);
        } catch (NotFoundException e) {
            log.warn("Medicação não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar medicação. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar medicação. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicacaoResponse> listar(Pageable pageable) {
        log.debug("Listando medicações paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Medicacao> medicacoes = medicacaoRepository.findAll(pageable);
            log.debug("Listagem de medicações concluída. Total de elementos: {}", medicacoes.getTotalElements());
            return medicacoes.map(medicacaoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar medicações. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar medicações", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar medicações. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacao", key = "#id")
    public MedicacaoResponse atualizar(UUID id, MedicacaoRequest request) {
        log.debug("Atualizando medicação. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de medicação");
            throw new BadRequestException("ID da medicação é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de medicação. ID: {}", id);
            throw new BadRequestException("Dados da medicação são obrigatórios");
        }

        try {

            Medicacao medicacaoExistente = medicacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

            medicacaoMapper.updateFromRequest(request, medicacaoExistente);

            if (request.getFabricanteEntity() != null) {
                FabricantesMedicamento fabricante = fabricantesMedicamentoRepository.findById(request.getFabricanteEntity())
                        .orElseThrow(() -> new NotFoundException("Fabricante não encontrado com ID: " + request.getFabricanteEntity()));
                medicacaoExistente.setFabricanteEntity(fabricante);
            } else {

                medicacaoExistente.setFabricanteEntity(null);
            }

            Medicacao medicacaoAtualizado = medicacaoRepository.save(medicacaoExistente);
            log.info("Medicação atualizada com sucesso. ID: {}", medicacaoAtualizado.getId());

            return medicacaoMapper.toResponse(medicacaoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar medicação não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar medicação. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar medicação. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar medicação. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacao", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo medicação. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de medicação");
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        try {
            Medicacao medicacao = medicacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(medicacao.getActive())) {
                log.warn("Tentativa de excluir medicação já inativa. ID: {}", id);
                throw new BadRequestException("Medicação já está inativa");
            }

            medicacao.setActive(false);
            medicacaoRepository.save(medicacao);
            log.info("Medicação excluída (desativada) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir medicação não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir medicação. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir medicação. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir medicação", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir medicação. ID: {}", id, e);
            throw e;
        }
    }

    private void garantirValoresPadrao(Medicacao medicacao) {

        if (medicacao.getIdentificacao() == null) {
            medicacao.setIdentificacao(IdentificacaoMedicamento.builder()
                    .principioAtivo("Não informado")
                    .nomeComercial("Não informado")
                    .build());
        } else {
            IdentificacaoMedicamento identificacao = medicacao.getIdentificacao();
            if (identificacao.getPrincipioAtivo() == null || identificacao.getPrincipioAtivo().trim().isEmpty()) {
                identificacao.setPrincipioAtivo("Não informado");
            }
            if (identificacao.getNomeComercial() == null || identificacao.getNomeComercial().trim().isEmpty()) {
                identificacao.setNomeComercial("Não informado");
            }
        }

        if (medicacao.getDosagemAdministracao() == null) {
            medicacao.setDosagemAdministracao(DosagemAdministracaoMedicamento.builder()
                    .dosagem("Não informado")
                    .build());
        } else {
            DosagemAdministracaoMedicamento dosagem = medicacao.getDosagemAdministracao();
            if (dosagem.getDosagem() == null || dosagem.getDosagem().trim().isEmpty()) {
                dosagem.setDosagem("Não informado");
            }
        }

        if (medicacao.getContraindicacoesPrecaucoes() == null) {
            medicacao.setContraindicacoesPrecaucoes(ContraindicacoesPrecaucoesMedicamento.builder()
                    .gestantePode(false)
                    .lactantePode(false)
                    .criancaPode(true)
                    .idosoPode(true)
                    .build());
        } else {
            ContraindicacoesPrecaucoesMedicamento contraindicacoes = medicacao.getContraindicacoesPrecaucoes();
            if (contraindicacoes.getGestantePode() == null) {
                contraindicacoes.setGestantePode(false);
            }
            if (contraindicacoes.getLactantePode() == null) {
                contraindicacoes.setLactantePode(false);
            }
            if (contraindicacoes.getCriancaPode() == null) {
                contraindicacoes.setCriancaPode(true);
            }
            if (contraindicacoes.getIdosoPode() == null) {
                contraindicacoes.setIdosoPode(true);
            }
        }

        if (medicacao.getConservacaoArmazenamento() == null) {
            medicacao.setConservacaoArmazenamento(ConservacaoArmazenamentoMedicamento.builder()
                    .protegerLuz(false)
                    .protegerUmidade(false)
                    .build());
        } else {
            ConservacaoArmazenamentoMedicamento conservacao = medicacao.getConservacaoArmazenamento();
            if (conservacao.getProtegerLuz() == null) {
                conservacao.setProtegerLuz(false);
            }
            if (conservacao.getProtegerUmidade() == null) {
                conservacao.setProtegerUmidade(false);
            }
        }

        if (medicacao.getRegistroControle() == null) {
            medicacao.setRegistroControle(RegistroControleMedicamento.builder()
                    .controlado(false)
                    .receitaObrigatoria(false)
                    .usoContinuo(false)
                    .medicamentoEspecial(false)
                    .medicamentoExcepcional(false)
                    .build());
        } else {
            RegistroControleMedicamento registro = medicacao.getRegistroControle();
            if (registro.getControlado() == null) {
                registro.setControlado(false);
            }
            if (registro.getReceitaObrigatoria() == null) {
                registro.setReceitaObrigatoria(false);
            }
            if (registro.getUsoContinuo() == null) {
                registro.setUsoContinuo(false);
            }
            if (registro.getMedicamentoEspecial() == null) {
                registro.setMedicamentoEspecial(false);
            }
            if (registro.getMedicamentoExcepcional() == null) {
                registro.setMedicamentoExcepcional(false);
            }
        }
    }

}
