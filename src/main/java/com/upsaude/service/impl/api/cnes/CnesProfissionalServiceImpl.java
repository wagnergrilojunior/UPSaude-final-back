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

            if (persistir) {
                final boolean[] isNovo = { false };
                ProfissionaisSaude profissional = profissionaisRepository.findByCnsAndTenant(numeroCns, tenant)
                        .orElseGet(() -> {
                            isNovo[0] = true;
                            ProfissionaisSaude novo = new ProfissionaisSaude();
                            novo.setTenant(tenant);
                            return novo;
                        });

                cnesMapper.mapToProfissional(dadosCnes, profissional);
                profissionaisRepository.save(Objects.requireNonNull(profissional));

                sincronizacaoService.finalizarComSucesso(registro.getId(), isNovo[0] ? 1 : 0, isNovo[0] ? 0 : 1);
            } else {
                sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            }

            return sincronizacaoService.obterPorId(registro.getId());

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

            if (persistir) {
                final boolean[] isNovo = { false };
                ProfissionaisSaude profissional = profissionaisRepository.findByCpfAndTenant(numeroCpf, tenant)
                        .orElseGet(() -> {
                            isNovo[0] = true;
                            ProfissionaisSaude novo = new ProfissionaisSaude();
                            novo.setTenant(tenant);
                            return novo;
                        });

                cnesMapper.mapToProfissional(dadosCnes, profissional);
                profissionaisRepository.save(Objects.requireNonNull(profissional));

                sincronizacaoService.finalizarComSucesso(registro.getId(), isNovo[0] ? 1 : 0, isNovo[0] ? 0 : 1);
            } else {
                sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            }

            return sincronizacaoService.obterPorId(registro.getId());

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
}
