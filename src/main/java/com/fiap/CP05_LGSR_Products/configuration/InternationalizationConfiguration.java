package com.fiap.CP05_LGSR_Products.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class InternationalizationConfiguration implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver()
    {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public MessageSource messageSource()
    {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("internationalization/messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        return messageSource;
    }
}
