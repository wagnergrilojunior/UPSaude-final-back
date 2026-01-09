package com.upsaude.validation.web;

import com.upsaude.exception.InvalidArgumentException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Resolver global para Pageable com validação de paginação e sort.
 * Evita page/size inválidos e padroniza erro via InvalidArgumentException (HTTP 400).
 * Também mapeia aliases comuns como "codigo" para "codigoOficial".
 */
public class ValidatedPageableResolver implements HandlerMethodArgumentResolver {

    private final PageableHandlerMethodArgumentResolver delegate;
    private final int maxSize;

    public ValidatedPageableResolver(int maxSize) {
        this.maxSize = maxSize;
        // Criar SortResolver customizado que mapeia "codigo" para "codigoOficial"
        SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver() {
            @Override
            public Sort resolveArgument(
                    @NonNull MethodParameter parameter,
                    ModelAndViewContainer mavContainer,
                    @NonNull NativeWebRequest webRequest,
                    WebDataBinderFactory binderFactory) {
                Sort sort = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
                // Mapeia aliases para propriedades reais
                if (sort != null && sort.isSorted()) {
                    List<Sort.Order> mappedOrders = sort.stream()
                            .map(order -> {
                                String property = order.getProperty();
                                // Mapeia "codigo" para "codigoOficial" (comum em entidades SIGTAP)
                                if ("codigo".equals(property)) {
                                    return new Sort.Order(order.getDirection(), "codigoOficial");
                                }
                                return order;
                            })
                            .collect(Collectors.toList());
                    return Sort.by(mappedOrders);
                }
                return sort;
            }
        };
        this.delegate = new PageableHandlerMethodArgumentResolver(sortResolver);
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return delegate.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Object resolved = delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        if (!(resolved instanceof Pageable pageable)) {
            return resolved;
        }

        if (pageable.getPageNumber() < 0) {
            throw new InvalidArgumentException("Paginação inválida: 'page' não pode ser negativo.");
        }
        if (pageable.getPageSize() <= 0) {
            throw new InvalidArgumentException("Paginação inválida: 'size' deve ser maior que zero.");
        }
        if (maxSize > 0 && pageable.getPageSize() > maxSize) {
            throw new InvalidArgumentException(String.format("Paginação inválida: 'size' não pode ser maior que %d.", maxSize));
        }

        // valida sort básico (o mapeamento de "codigo" para "codigoOficial" já foi feito no SortResolver)
        pageable.getSort().forEach(order -> {
            String prop = order.getProperty();
            if (prop == null || prop.isBlank()) {
                throw new InvalidArgumentException("Paginação inválida: 'sort' contém propriedade vazia.");
            }
            // evita caracteres suspeitos; mantém compatibilidade com nested props (a.b)
            if (!prop.matches("^[a-zA-Z0-9_.]+$")) {
                throw new InvalidArgumentException(String.format("Paginação inválida: 'sort' contém propriedade inválida: '%s'.", prop));
            }
        });

        return pageable;
    }
}

