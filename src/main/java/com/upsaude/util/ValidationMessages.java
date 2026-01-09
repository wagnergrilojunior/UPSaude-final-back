package com.upsaude.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Classe utilitária para acessar mensagens de validação centralizadas.
 * 
 * <p>
 * Esta classe fornece métodos estáticos para recuperar mensagens de validação
 * do arquivo de propriedades centralizado.
 * </p>
 * 
 * <p>
 * Exemplo de uso:
 * <pre>
 * // Sem parâmetros
 * String mensagem = ValidationMessages.get("validation.cpf.invalido");
 * 
 * // Com um parâmetro
 * String mensagem = ValidationMessages.get("validation.cpf.duplicado", "12345678901");
 * 
 * // Com múltiplos parâmetros
 * String mensagem = ValidationMessages.get("validation.job.limite.excedido", count, limit);
 * </pre>
 * </p>
 */
@Component
public class ValidationMessages {

    private static MessageSource messageSource;

    public ValidationMessages(MessageSource messageSource) {
        ValidationMessages.messageSource = messageSource;
    }

    /**
     * Obtém uma mensagem de validação sem parâmetros.
     * 
     * @param code Código da mensagem (chave no arquivo de propriedades)
     * @return Mensagem formatada
     */
    public static String get(String code) {
        return getMessage(code, null);
    }

    /**
     * Obtém uma mensagem de validação com um parâmetro.
     * 
     * @param code Código da mensagem
     * @param arg Parâmetro para substituição na mensagem
     * @return Mensagem formatada
     */
    public static String get(String code, Object arg) {
        return getMessage(code, new Object[]{arg});
    }

    /**
     * Obtém uma mensagem de validação com múltiplos parâmetros.
     * 
     * @param code Código da mensagem
     * @param args Parâmetros para substituição na mensagem
     * @return Mensagem formatada
     */
    public static String get(String code, Object... args) {
        return getMessage(code, args);
    }

    /**
     * Obtém uma mensagem de validação usando um Locale específico.
     * 
     * @param code Código da mensagem
     * @param locale Locale a ser usado
     * @param args Parâmetros para substituição
     * @return Mensagem formatada
     */
    public static String get(String code, Locale locale, Object... args) {
        if (messageSource == null) {
            throw new IllegalStateException("MessageSource não foi inicializado. Certifique-se de que ValidationMessagesConfig está configurado.");
        }
        return messageSource.getMessage(code, args, code, locale);
    }

    /**
     * Método interno para obter mensagens usando o Locale do contexto.
     * 
     * @param code Código da mensagem
     * @param args Parâmetros
     * @return Mensagem formatada
     */
    private static String getMessage(String code, Object[] args) {
        if (messageSource == null) {
            throw new IllegalStateException("MessageSource não foi inicializado. Certifique-se de que ValidationMessagesConfig está configurado.");
        }
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args != null ? args : new Object[0], code, locale);
    }
}
