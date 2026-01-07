package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.EquipamentoCnesSoapClient;
import com.upsaude.service.api.cnes.CnesEquipamentoService;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import com.upsaude.integration.cnes.wsdl.equipamento.*;
import com.upsaude.mapper.cnes.CnesEquipamentoMapper;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosRepository;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesEquipamentoServiceImpl implements CnesEquipamentoService {

    private final EquipamentoCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final TenantService tenantService;
    private final EquipamentosRepository equipamentoRepository;
    private final CnesEquipamentoMapper cnesMapper;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarEquipamentosPorEstabelecimento(String codigoCnes, boolean persistir) {
        log.info("Iniciando sincronização de equipamentos por estabelecimento: {}", codigoCnes);

        CnesValidator.validarCnes(codigoCnes);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.EQUIPAMENTO,
                null,
                codigoCnes,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            ResponseConsultarEquipamentos resposta = soapClient.consultarEquipamentos(codigoCnes);

            int inseridos = 0;
            int atualizados = 0;

            if (resposta != null && resposta.getEquipamento() != null) {
                List<EquipamentoType> equipamentosCnes = resposta.getEquipamento();

                if (persistir) {
                    for (EquipamentoType equipamentoCnes : equipamentosCnes) {
                        final boolean[] isNovo = { false };
                        Equipamentos equipamento = equipamentoRepository
                                .findByCodigoCnesAndTenantId(equipamentoCnes.getCodigo(), tenant.getId())
                                .orElseGet(() -> {
                                    isNovo[0] = true;
                                    Equipamentos novo = new Equipamentos();
                                    novo.setTenant(tenant);
                                    return novo;
                                });

                        cnesMapper.mapToEquipamento(equipamentoCnes, equipamento);
                        equipamentoRepository.save(Objects.requireNonNull(equipamento));

                        if (isNovo[0])
                            inseridos++;
                        else
                            atualizados++;
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), inseridos, atualizados);

            return sincronizacaoService.obterPorId(registro.getId());

        } catch (Exception e) {
            log.error("Erro ao sincronizar equipamentos: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar equipamentos: " + e.getMessage(), e);
        }
    }
}
