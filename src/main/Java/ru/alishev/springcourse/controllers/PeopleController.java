package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.springcourse.dao.PersonDAO;


@Controller
@RequestMapping("/people") // Все адреса в этом контроллере будут начинаться '/people'
public class PeopleController {


//    @Autowired   // Spring внедрит объект класса 'PersonDAO' в наш контроллер НО ЛУЧШЕ ИСПОЛЬЗОВАТЬ ЧЕРЕЗ КОНСТРУКТОР, что мы и сделали ниже.
    private PersonDAO personDAO;



    @Autowired   // а тут мы в таком случае можем и не писать @Autowired, всеравно внедрит.
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }



    /*
           Возвращает список из людей. набрав '/people' и сделав GET -запрос
        Получим всех людей из  DAO и передадим на отображение
        этих людей в предствление и с помощью thymeleaf
        отобразим это представление
         */
    @GetMapping()
    public String index(Model model) { // в модели будем передавать людей на представление
        model.addAttribute("people", personDAO.index()); // под ключем 'people' у нас будет лежать список из людей
        return "people/index";   // вернем ту страницу (шаблон), который будет отображать список из людей
    }


    /*
    на вход будет принимать ___ и к '/people' будет добавлено '/id'
    '/{id}' -dj время того, как мы запустим наше приложение в 'id'
    можно будет поместить любое число и тогда это число поместиться
    в аргументы этого метода. С помощью аннотации @PathVariable("id")
    мы извдечем этот 'id' из URL адреса и получим к нему доступ внутри этого метода
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { // в 'id' будет лежать то целое число, кот. прередается в адресе к этому методу. Model -будем передавать 1 человека в шаблон
        /* получим 1 человека из DAO по id и передадим его на представление. */
        model.addAttribute("person", personDAO.show(id));   // под ключем 'person' будет лежать то, чтро пришло из DAO по 'id' т.е. конкретный человек, которого мы нашли по id
        return "people/show";   // вернем ту страницу (шаблон), где будет отображать этот человек
    }

    /*
    После реализации этих 2-х методов
    'index(Model model)' и 'show(@PathVariable("id") int id, Model model)'
    нам остается реализовать 2 шаблона:
       1. будет отображать список из людей
       2. будет показывать 1-го человека.
     */

}
