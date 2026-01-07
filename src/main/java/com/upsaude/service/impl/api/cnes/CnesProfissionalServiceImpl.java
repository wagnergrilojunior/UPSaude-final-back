package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSoapException;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.ProfissionalCnesSoapClient;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.service.api.cnes.CnesProfissionalService;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import com.upsaude.mapper.cnes.CnesProfissionalMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesProfissionalServiceImpl implements CnesProfissionalService {

    private final ProfissionalCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final ProfissionaisSaudeRepository profissionaisRepository;
    private final TenantService tenantService;
    private final CnesProfissionalMapper cnesMapper;
    private final com.upsaude.mapper.profissional.ProfissionalSaudeMapper profissionalResponseMapper;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarProfissionalPorCns(String numeroCns, boolean persistir) {
        log.info("Iniciando sincronização de profissional por CNS: {}", numeroCns);

        CnesValidator.validarCns(numeroCns);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.PROFISSIONAL,
                null,
                numeroCns,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            com.upsaude.integration.cnes.wsdl.profissional.ResponseConsultarProfissionalSaude resposta = soapClient
                    .consultarProfissionalPorCns(numeroCns);

            if (resposta == null || resposta.getProfissionalSaude() == null) {
                throw new CnesSincronizacaoException("Profissional não encontrado no CNES");
            }

            com.upsaude.integration.cnes.wsdl.profissional.ProfissionalSaudeType dadosCnes = resposta
                    .getProfissionalSaude();

            ProfissionaisSaude profissional = null;
            if (persistir) {
                final boolean[] isNovo = { false };
                profissional = profissionaisRepository.findByCnsAndTenant(numeroCns, tenant)
                        .orElseGet(() -> {
                            isNovo[0] = true;
                            ProfissionaisSaude novo = new ProfissionaisSaude();
                            novo.setTenant(tenant);
                            return novo;
                        });

                cnesMapper.mapToProfissional(dadosCnes, profissional);
                profissional = profissionaisRepository.save(Objects.requireNonNull(profissional));

                sincronizacaoService.finalizarComSucesso(registro.getId(), isNovo[0] ? 1 : 0, isNovo[0] ? 0 : 1);
            } else {
                sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            }

            CnesSincronizacaoResponse response = sincronizacaoService.obterPorId(registro.getId());
            if (persistir && profissional != null) {
                response.setEntidade(profissionalResponseMapper.toResponse(profissional));
            }
            return response;

        } catch (CnesSoapException e) {
            log.error("Erro SOAP ao sincronizar profissional CNS: {}", numeroCns, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro SOAP: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar profissional: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao sincronizar profissional CNS: {}", numeroCns, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar profissional: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarProfissionalPorCpf(String numeroCpf, boolean persistir) {
        log.info("Iniciando sincronização de profissional por CPF: {}", numeroCpf);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.PROFISSIONAL,
                null,
                numeroCpf,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            com.upsaude.integration.cnes.wsdl.profissional.ResponseConsultarProfissionalSaude resposta = soapClient
                    .consultarProfissionalPorCpf(numeroCpf);

            if (resposta == null || resposta.getProfissionalSaude() == null) {
                throw new CnesSincronizacaoException("Profissional não encontrado no CNES");
            }

            com.upsaude.integration.cnes.wsdl.profissional.ProfissionalSaudeType dadosCnes = resposta
                    .getProfissionalSaude();

            ProfissionaisSaude profissional = null;
            if (persistir) {
                final boolean[] isNovo = { false };
                profissional = profissionaisRepository.findByCpfAndTenant(numeroCpf, tenant)
                        .orElseGet(() -> {
                            isNovo[0] = true;
                            ProfissionaisSaude novo = new ProfissionaisSaude();
                            novo.setTenant(tenant);
                            return novo;
                        });

                cnesMapper.mapToProfissional(dadosCnes, profissional);
                profissional = profissionaisRepository.save(Objects.requireNonNull(profissional));

                sincronizacaoService.finalizarComSucesso(registro.getId(), isNovo[0] ? 1 : 0, isNovo[0] ? 0 : 1);
            } else {
                sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            }

            CnesSincronizacaoResponse response = sincronizacaoService.obterPorId(registro.getId());
            if (persistir && profissional != null) {
                response.setEntidade(profissionalResponseMapper.toResponse(profissional));
            }
            return response;

        } catch (Exception e) {
            log.error("Erro ao sincronizar profissional CPF: {}", numeroCpf, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar profissional: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object buscarProfissionalNoCnes(String cnsOuCpf) {
        log.debug("Buscando profissional no CNES: {}", cnsOuCpf);

        boolean isCns = cnsOuCpf.length() == 15;

        com.upsaude.integration.cnes.wsdl.profissional.ResponseConsultarProfissionalSaude resposta;
        if (isCns) {
            resposta = soapClient.consultarProfissionalPorCns(cnsOuCpf);
        } else {
            resposta = soapClient.consultarProfissionalPorCpf(cnsOuCpf);
        }

        if (resposta == null || resposta.getProfissionalSaude() == null) {
            return null;
        }

        return resposta.getProfissionalSaude();
    }

    @Override
    @Transactional(readOnly = true)
    public Object buscarProfissionalPorCpf(String cpf) {
        log.debug("Buscando profissional sincronizado por CPF: {}", cpf);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        return profissionaisRepository.findByCpfAndTenant(cpf, tenant)
                .map(profissionalResponseMapper::toResponse)
                .orElseThrow(() -> new com.upsaude.exception.NotFoundException(
                        "Profissional com CPF " + cpf + " não encontrado no banco local"));
    }

    @Override
    @Transactional(readOnly = true)
    public Object buscarProfissionalPorCns(String cns) {
        log.debug("Buscando profissional sincronizado por CNS: {}", cns);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        return profissionaisRepository.findByCnsAndTenant(cns, tenant)
                .map(profissionalResponseMapper::toResponse)
                .orElseThrow(() -> new com.upsaude.exception.NotFoundException(
                        "Profissional com CNS " + cns + " não encontrado no banco local"));
    }

    @Override
    @Transactional(readOnly = true)
    public Object listarProfissionais(int page, int size) {
        log.debug("Listando profissionais sincronizados - page: {}, size: {}", page, size);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);

        org.springframework.data.domain.Page<ProfissionaisSaude> profissionais = profissionaisRepository
                .findAllByTenant(tenant.getId(), pageable);
        return profissionalResponseMapper.toResponsePage(profissionais);
    }
}
