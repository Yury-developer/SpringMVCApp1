package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;


    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }



    @GetMapping() // показ. все люди
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") // показ. конкретн.чел.
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }



    /*
    В этом уроке № 22 мы добавим 2 метода:
    1. возвращать HTML - форму для создания НОВОГО чел.
    2. будет принимать POS -запрос, брать оттуда данные и добавлять человека в БАЗУ с помощью DAO
     */

    // Создает нового чел. и добав. его в БД

    @PostMapping()   // никакого адреса не передаем, т.к. по POS -запросу '/people' мы должны попасть в этот метод
    public String create(@ModelAttribute("person") Person person) { // в этом объекте класса 'Person' будет 'лежать человек с данными из формы'
        personDAO.save(person);   // после эт. реализуем этот метод 'save'. После всего мы должня вернуть какую-то страницу для клиента.
        return "redirect:/people";   //Вернем ту страницу, на которую хотим совершить 'redirect' и браузер получит эту страницу и сделает запрос к указанному адресу. (используем механизм REDIRECT)
    }

    /* В ЭТОМ методе 'create(@ModelAttribute("person") Person person)'   аннотация   '@ModelAttribute'
         1. создает объект;
         2. считывает данные из поля;
         3. помещает эти данные в объект;
         4. помещает этот объект в модель.
       В методе 'newPerson(Model model)'  эта аннотация '@ModelAttribute' не увидит этих полей (т.к. их нету) в поступающем GET -запросе,
       соотв-но она просто создасть пустой объект класса 'Person' с пустым конструктором и его поместит в модель.

       Поэтому исходный код:

    @GetMapping("/new")   // т.е. по запросу '/people/new' нам в браузер вернется HTML форма для созд.нов.чел.
    public String newPerson(Model model) {   // Если мы используем thymeleaf -формы, мы должны им передовать тот объект, для которого эта форма нужна.
        model.addAttribute("person", new Person());   // и создадим в классе Person пустой конструктор.
        return "people/new";   // название того thymeleaf шаблона, для создания Нового чел.
    }

       Будет заменен на код:
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {   // '@ModelAttribute' создаст объект класса 'Person' и положит его сама в модель
        return "people/new";
    }

     */
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {   // '@ModelAttribute' создаст объект класса 'Person' и положит его сама в модель
        return "people/new";
    }

}
