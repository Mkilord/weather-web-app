package ru.mkilord.wetherwebapp.config;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleConfig {
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public I18NProvider i18nProvider(MessageSource messageSource) {
        return new I18NProvider() {
            @Override
            public List<Locale> getProvidedLocales() {
                return List.of(Locale.ENGLISH, Locale.of("ru", "RU"));
            }

            @Override
            public String getTranslation(String s, Locale locale, Object... objects) {
                return messageSource.getMessage(s, objects, locale);
            }
        };
    }
}
