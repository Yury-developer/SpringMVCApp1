package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {

    @GetMapping("/exit")   // хотим принимать на вход GET-запросы
    public String helloPage() {
        return "second/exit";   // возвращает название представления
    }

}
