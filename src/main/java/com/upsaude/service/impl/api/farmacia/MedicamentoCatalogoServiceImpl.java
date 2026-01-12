package com.upsaude.service.impl.api.farmacia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.response.farmacia.MedicamentoResponse;
import com.upsaude.api.response.farmacia.PrincipioAtivoResponse;
import com.upsaude.api.response.farmacia.UnidadeMedidaResponse;
import com.upsaude.api.response.farmacia.ViaAdministracaoResponse;
import com.upsaude.mapper.farmacia.MedicamentoMapper;
import com.upsaude.mapper.farmacia.PrincipioAtivoMapper;
import com.upsaude.mapper.farmacia.UnidadeMedidaMapper;
import com.upsaude.mapper.farmacia.ViaAdministracaoMapper;
import com.upsaude.repository.farmacia.MedicamentoRepository;
import com.upsaude.repository.farmacia.PrincipioAtivoRepository;
import com.upsaude.repository.farmacia.UnidadeMedidaRepository;
import com.upsaude.repository.vacinacao.ViaAdministracaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.service.api.farmacia.MedicamentoCatalogoService;
import com.upsaude.mapper.sigtap.SigtapProcedimentoMapper;
import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicamentoCatalogoServiceImpl implements MedicamentoCatalogoService {

    private final PrincipioAtivoRepository principioAtivoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UnidadeMedidaRepository unidadeMedidaRepository;
    private final ViaAdministracaoRepository viaAdministracaoRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;

    private final PrincipioAtivoMapper principioAtivoMapper;
    private final MedicamentoMapper medicamentoMapper;
    private final UnidadeMedidaMapper unidadeMedidaMapper;
    private final ViaAdministracaoMapper viaAdministracaoMapper;
    private final SigtapProcedimentoMapper sigtapProcedimentoMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PrincipioAtivoResponse> listarPrincipiosAtivos(String termo, Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return principioAtivoRepository.buscarPorTermo(termo, pageable).map(principioAtivoMapper::toResponse);
        }
        return principioAtivoRepository.findByAtivoTrue(pageable).map(principioAtivoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicamentoResponse> listarMedicamentos(String termo, Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return medicamentoRepository.buscarPorTermo(termo, pageable).map(medicamentoMapper::toResponse);
        }
        return medicamentoRepository.findByAtivoTrue(pageable).map(medicamentoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SigtapProcedimentoResponse> listarMedicamentosSigtap(String termo, Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return sigtapProcedimentoRepository.buscarMedicamentos(termo, pageable)
                    .map(sigtapProcedimentoMapper::toResponse);
        }
        return sigtapProcedimentoRepository.buscarMedicamentos("", pageable).map(sigtapProcedimentoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnidadeMedidaResponse> listarUnidadesMedida(String termo, Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return unidadeMedidaRepository.buscarPorTermo(termo, pageable).map(unidadeMedidaMapper::toResponse);
        }
        return unidadeMedidaRepository.findByAtivoTrue(pageable).map(unidadeMedidaMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ViaAdministracaoResponse> listarViasAdministracao(String termo, Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return viaAdministracaoRepository.buscarPorTermo(termo, pageable).map(viaAdministracaoMapper::toResponse);
        }
        return viaAdministracaoRepository.findByAtivoTrue(pageable).map(viaAdministracaoMapper::toResponse);
    }
}
