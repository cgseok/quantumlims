package lims.core.config;


import lims.core.audit.interceptor.AuditRequestLogInterceptor;
import lims.core.common.message.QuantumReloadableResourceBundleMessageSource;
import lims.core.login.interceptor.LoginSessionCheckInterceptor;
import lims.core.web.QuantumJsonViewResolver;
import lims.core.web.QuantumParameterMapArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "lims")
public class QuantumWebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private LoginSessionCheckInterceptor loginSessionCheckInterceptor;
    @Autowired
    private QuantumParameterMapArgumentResolver quantumParameterMapArgumentResolver;

    @Autowired
    private AuditRequestLogInterceptor auditRequestLogInterceptor;

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();


        resolvers.add(jsonViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Bean
    public ViewResolver jsonViewResolver() {
        return new QuantumJsonViewResolver();
    }

    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(quantumParameterMapArgumentResolver);
    }

    @Bean(name = "messageSource")
    public QuantumReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        QuantumReloadableResourceBundleMessageSource messageSource = new QuantumReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/application_message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        Locale.setDefault(Locale.US);
        // 언어&국가정보가 없는 경우 미국으로 인식하도록 설정
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
    }

    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        list.add(MediaType.MULTIPART_FORM_DATA);
        return list;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginSessionCheckInterceptor)
                .addPathPatterns("/user/**")
                .addPathPatterns("/member/**")
                .addPathPatterns("/systemAuthUser/**")
                .excludePathPatterns("/user/idpwLogin")
                /* .excludePathPatterns("/user/refreshLogin") */
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-ui.html")
        ;

        registry.addInterceptor(auditRequestLogInterceptor)
                .addPathPatterns("/user/**")
                .addPathPatterns("/member/**")
//			.addPathPatterns("/systemAuthUser/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-ui.html")
        ;

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");

    }

    /*
    @Bean
    public PropertiesFactoryBean propertiesFactoryBean() {
    	PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    	ClassPathResource classPathResource = new ClassPathResource("properties/system.properties");
    	propertiesFactoryBean.setLocation(classPathResource);
        return propertiesFactoryBean;
    }
    */
}
