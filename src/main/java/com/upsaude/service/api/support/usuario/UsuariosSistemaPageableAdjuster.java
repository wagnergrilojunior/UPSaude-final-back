package com.upsaude.service.api.support.usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsuariosSistemaPageableAdjuster {

    public Pageable ajustarParaCamposEmbeddados(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return pageable;
        }

        List<Sort.Order> adjustedOrders = new ArrayList<Sort.Order>();
        Iterator<Sort.Order> iterator = pageable.getSort().iterator();

        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            String property = order.getProperty();

            if ("username".equals(property)) {
                property = "dadosIdentificacao." + property;
            } else if ("nomeExibicao".equals(property) || "fotoUrl".equals(property)) {
                property = "dadosExibicao." + property;
            } else if ("adminTenant".equals(property) || "adminSistema".equals(property)) {
                property = "configuracao." + property;
            }

            Sort.Order newOrder = new Sort.Order(order.getDirection(), property);
            adjustedOrders.add(newOrder);
        }

        Sort adjustedSort = Sort.unsorted();
        if (!adjustedOrders.isEmpty()) {
            adjustedSort = Sort.by(adjustedOrders);
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), adjustedSort);
    }
}
