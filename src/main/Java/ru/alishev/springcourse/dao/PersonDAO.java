package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;
import java.util.ArrayList;
import java.util.List;


/*
 Этот класс будет общаться со СПИСКОМ: извлекать/ добавлять/ удалять человека из списка
 Обычно в DAO мы общаемся с БД, но тут упрощенно будет, список
 */
@Component
public class PersonDAO {
    private List<Person> people; // список
    private static int PEOPLE_COUNT; // счетчик людей для формирования 'id'


    // используем блок инициализации, можно конструктор
    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Kris"));
        people.add(new Person(++PEOPLE_COUNT, "Julia"));
        people.add(new Person(++PEOPLE_COUNT, "Katerina"));
        people.add(new Person(++PEOPLE_COUNT, "Masha"));
    }


    // просто возвращает список из людей, который потом с помощью thymeleaf мы отобразим в браузере
    public List<Person> index() {
        return people;
    }


    // возвращает 1-го человека по id
    public Person show(int id) {
        // находим человека с этим id, если такого человека нет - то возвращаем null
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }


    // добавляем нового чел. в наш список (урок №22)
    public void save(Person person) {   // ничего не возвращаем.
        // в форме 'people/new' (метод 'newPerson(Model model)' класса 'PeopleController') мы не будем сами указывать id для нового чел.
        person.setId(++PEOPLE_COUNT);   // id будем назначать динамически, т.е. пользователь не будет его вводить вручную!
        people.add(person);
    }


    /*
    дописываем на этом уроке №23
     */
    public void update(int id, Person updatedPerson) {   // 'updatedPerson' -новые значения
        Person personToBeUpdated = show(id);   // находим 'человека' которого необходимо обновит. Если бы было несколько полей, то обновили бы все поля.
        personToBeUpdated.setName(updatedPerson.getName());   // обновляем у него поле 'name' тем полем, которое пришло из формы редактирования
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);   // в ArrayList есть метод удаления по предикату, если   '(p -> p.getId() == id)'   true -> то элемент будет удален из нашего списка
        // проходимся по каждому человеку из нашего списка, у него вызываем '.getId()' и если id равны, то удаляем
    }

}
