package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller   // эта аннотация будет отсканирована с помощью   SpringConfig.java   аннотацией   @ComponentScan("ru.alishev.springcourse")
@RequestMapping("/first")   // Все адреса будут иметь префикс   '/first'
public class FirstController {

    // К этому методу контнроллера мы сможем обратиться по адресу   '/first/hello'
    @GetMapping("/hello")   // хотим принимать на вход GET-запросы
    public String helloPage() {
        return "first/hello";   // возвращает название представления
    }

    // К этому методу контнроллера мы сможем обратиться по адресу   '/first/goodbye'
    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }

}
