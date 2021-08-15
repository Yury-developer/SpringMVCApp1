package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;

import javax.validation.Valid;


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

    // Урок 24:
	// Мы добавляем анотацию '@Valid'. Она используется на самом классе 'Person',
	// т.е. на самой модели. Теперь на этапе внедрения значений из формы в класс 'Person'
	// анотацию '@Valid' будет проверяться, что значения соответствуют тем условиям,
	// которые мы декларировали (описали) в самом классе 'Person'
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
						 BindingResult bindingResult) {   // Если условия нарушаются
    	  // - то появляется ошибка, которая помещается в отдельный объект
		  // (ВАЖНО: этот объект должен идти всегда после той модели, которая валидируется.
		  // того класса, на кот.стоит аннотация '@Valid'.). Здесь будут лежать все ошибки класса 'Person'

		  if (bindingResult.hasErrors()) {
		  	  /*
		  	  Если на этом объекте есть ошибки (класса 'Person') - то мы не идем дальше,
		  	  а возвращаем ту-же самую форму для создания нового человека.
		  	  НО в этой форме уже будут присутствовать ошибки и они будут показваться
		  	  с помощью 'thymeleaf', т.к. они внедрились автоматически с помощью аннотации '@Valid'.
		  	   */
		  	  return "people/new";
		  }

		personDAO.save(person);
		return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {   // '@ModelAttribute' создаст объект класса 'Person' и положит его сама в модель
        return "people/new";
    }


    /*
    Урок № 23:
    Создадим страницу для редактирования человека.
    Для этого сначала создадим новый метод в контроллере
     */
  	@GetMapping("/{id}/edit")   // при запросе '/people/{id}/edit   -мы попадем в этот метод.'
  	public String edit(Model model, @PathVariable("id") int id) {   // внедряем модель, извлекаем id, кот. передается в адресе запроса по ключу "id"
    	model.addAttribute("person", personDAO.show(id));   // в самой форме уже будут заначения текущего человека, для удобства редактирования
		// в модель внедряем xxtkjdtrf. т.к. хотим, чтобы поля были НЕ пустые, а в полях уже были значения текущего человека.
    	return "people/edit";   // HTML -страница для редактирования чел, пред.
		// затем создаем новое представление: edit.html
	}

	/*
	Урок № 23: 	метод принимает запрос методом 'Patch' на адрес '/people/{id}'
	Урок № 24: проделываем тут всё то-же, что и в методе 'create(...)' (см. мыше)
	 */
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
						 @PathVariable("id") int id) {

		  if (bindingResult.hasErrors()) {
		  	  return "people/edit";   // возвращаем ту самую страницу для редактирования человека
		  }

		personDAO.update(id, person);
		return "redirect:/people";
	}

	@DeleteMapping("/{id}")   // принимаем в адресе только id удаляемого человека
	public String delete(@PathVariable("id") int id) {
		personDAO.delete(id);
		return "redirect:/people";   // после успешного удаления редирект...
		// после чего реализуем метод 'delete' в DAO 'PersonDAO'
	}


}
