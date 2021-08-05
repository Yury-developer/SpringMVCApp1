package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller   // эта аннотация будет отсканирована с помощью   SpringConfig.java   аннотацией   @ComponentScan("ru.alishev.springcourse")
@RequestMapping("/first")   // Все адреса будут иметь префикс   '/first'
public class FirstController {

    // В ЭТОМ уроке №19 мы будем работать ТОЛЬКО С ЭТИМ методом ТОЛЬКО ЭТОГО контроллера (из урока №17)
    // К этому методу контнроллера мы сможем обратиться по адресу   '/first/hello'
    @GetMapping("/hello")
    // required = false   -означает, что если ничего не придет - то туда просто положится   null
    // по умолчанию   required = true   -означает, что если ничего не придет - то в браузер прилетит ошибка 400 "HTTP Status 400 – Bad Request"
    public String helloPage(@RequestParam(value = "name", required = false)String name,
                            @RequestParam(value = "surname", required = false)String surname) {

        System.out.println("Hello " + name + " " + surname);

        return "first/hello";   // возвращает название представления
    }

    // К этому методу контнроллера мы сможем обратиться по адресу   '/first/goodbye'
    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }

}
