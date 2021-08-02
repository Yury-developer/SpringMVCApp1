package ru.alishev.springcourse.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


// наша конфигурация будет полностью эквивалентна 'web.xml'
public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
            // этот метод мы использовать не будем, поэтому возвращаем null
        return null;
    }

    @Override   // здесь укажем класс конфигурации, заменяющий   'applicationContextMVC.xml'
    protected Class<?>[] getServletConfigClasses() {
            /*
            Здесь мы должны подставить наш конфигурационный класс
            так-же, как и в 'web.xml' мы подставляли путь до applicationContextMVC.xml
            <param-value>/WEB-INF/applicationContextMVC.xml</param-value>
            Так-же и в Java - конфигурации нужно подставить путь до 'SpringConfig'
         */
        return new Class[] {SpringConfig.class};
        /*
            Теперь наш Java класс, кот. исп. роль web.xml знает где находится конфигурация.
            Она находится в этом классе.
         */
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
        /*
            Это эквивалентно тому, что мы в файле 'web.xml' все http - запросы от пользователя
            посылаем на dispatcher-servlet
                <servlet-mapping>
                    <servlet-name>dispatcher</servlet-name>
                    <url-pattern>/</url-pattern>
                </servlet-mapping>
         */
    }
}
