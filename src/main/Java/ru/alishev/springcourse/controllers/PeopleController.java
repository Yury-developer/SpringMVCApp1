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



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
		personDAO.save(person);
		return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {   // '@ModelAttribute' создаст объект класса 'Person' и положит его сама в модель
        return "people/new";
    }


    /*
    на этов мроке № 23:
    Создадим страницу для редактирования человека.
    Для этого сначала создадим новый метод в контроллере
     */
  	@GetMapping("/{id}/edit")   // '/people/{id}/edit   -мы попадем в этот метод.'
  	public String edit(Model model, @PathVariable("id") int id) {   // внедряем модель, извлекаем id, кот. передается в адресе запроса
    	model.addAttribute("person", personDAO.show(id));   // в самой форме уже будут заначения текущего человека, для удобства редактирования
    	return "people/edit";   // HTML -страница для редактирования чел, пред.
	}

	/*
	метод принимает запрос методом 'Patch' на адрес '/people/{id}'
	 */
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {   // принимаем объект "person" из формы
		// теперь должны найти чел. из БД с таким id и поменять его значения
		// на те, которые пришли из формы, т.е. кот. лежат в объекте "person". Это делаем внутри DAO
		personDAO.update(id, person);   // после чего реализуем метод 'update'
		return "redirect:/people";
	}


}
