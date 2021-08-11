package ru.alishev.springcourse.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


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


    /*
    для урока № 23 создаем фильтр, который будет читать значение этого скрытого поля
    ИСПОЛЬЗУЕМ ОДИН ФИЛЬТР ДЛЯ ТОГО, ЧТОБЫ ЗАРАБОТАЛИ   'PATCH'   И   'DELETE'   ЗАПРОСЫ
     метод "onStartup" запускается при старте Spring приложения и здесь в приватном методе
     мы добавляем к нашему приложению один фильтр с названием 'HiddenHttpMethodFilter()'
     т.е. сами мы его не реализуем, он уже есть в Spring. Просто добавляем его.
     Фильтр перенаправляет входящие HTTP запросы на мужные методу контроллера

     В Spring BOOT это будет занимать всего 1 строку в конфигурационном файле.
     */
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }



}
