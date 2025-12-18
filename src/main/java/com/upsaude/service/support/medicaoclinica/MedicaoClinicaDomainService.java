package com.upsaude.service.support.medicaoclinica;

import com.upsaude.entity.medicao.MedicaoClinica;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MedicaoClinicaDomainService {

    public void aplicarDefaults(MedicaoClinica entity) {
        calcularImc(entity);
    }

    public void calcularImc(MedicaoClinica medicaoClinica) {
        if (medicaoClinica.getPeso() != null
            && medicaoClinica.getAltura() != null
            && medicaoClinica.getAltura().compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal alturaQuadrado = medicaoClinica.getAltura().multiply(medicaoClinica.getAltura());
            medicaoClinica.setImc(medicaoClinica.getPeso().divide(alturaQuadrado, 2, RoundingMode.HALF_UP));
        } else {
            medicaoClinica.setImc(null);
        }
    }
}
