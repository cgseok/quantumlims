package lims.core.common.message;

import java.util.Locale;
import java.util.Properties;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class QuantumReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        org.springframework.context.support.ReloadableResourceBundleMessageSource.PropertiesHolder propertiesHolder = getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();
        return properties;
    }
}
