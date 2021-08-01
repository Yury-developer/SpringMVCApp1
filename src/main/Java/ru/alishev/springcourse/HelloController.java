package ru.alishev.springcourse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Neil Alishev
 */
@Controller
public class HelloController {

    @GetMapping("/hello-world") // когда пользователь обратится по этому URL
    public String sayHello() {
        return "hello_world"; // пользователю вернется просто представление
    }
}