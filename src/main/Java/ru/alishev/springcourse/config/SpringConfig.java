package ru.alishev.springcourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;



@Configuration   // эта конфигурация полностью эквивалентна .xml конфигу в 'applicationContextMVC.xml'
@ComponentScan("ru.alishev.springcourse")   // тот пакет, где лежит КОНТРОЛЛЕР
@EnableWebMvc   // равноценна тэгу '<mvc:annotation-driven/>' в файле 'applicationContextMVC.xml'
public class SpringConfig implements WebMvcConfigurer {
    /*
     Нам остается реализовать те бины, кот. ответственны за конфигурацию шаблонизатора org.thymeleaf
     Этот интерфейс реализуется в том случае, когда мы хотимм под себя настоить Spring MVC
     с этим интерфейсом мы должны реализовать метод 'configureViewResolvers'
     В данном случае мы хотим вместо стандартного шаблонизатора использовать 'thymeleaf'
     */
    private final ApplicationContext applicationContext;



    @Autowired   // 'applicationContext' будет внедрен самим Spring вместо нас.
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;   // для настройки 'thymeleaf'
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");   // папка с представлениями
        templateResolver.setSuffix(".html");   // расширения этих представлений
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() { // произведем конфигурацию наших представлений
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    // Задаем шаблонизатор 'thymeleaf' (настраиваем под себя)
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }





}
