package lims.core.common.message;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class QuantumMessageUtil {

    private static QuantumReloadableResourceBundleMessageSource messageSource;

    protected transient Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = true)
    public QuantumMessageUtil(@Qualifier("messageSource") QuantumReloadableResourceBundleMessageSource messageSource) {
        QuantumMessageUtil.messageSource = messageSource;
    }

    public static String getMessagesToJson(Locale locale) {
        Gson gson = new Gson();
        return gson.toJson(messageSource.getAllProperties(locale));
    }

    public static String getMessage(String code) {

        return QuantumMessageUtil.getMessage(code, null, null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] messageParams) {
        return QuantumMessageUtil.getMessage(code, messageParams, null, Locale.getDefault());
    }

    public static String getMessage(String code, Object[] messageParams, String defaultMessage) {
        return QuantumMessageUtil.getMessage(code, messageParams, defaultMessage, Locale.getDefault());
    }

    public static String getMessage(String code, Object[] messageParams, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, messageParams, defaultMessage, locale);
    }

}
