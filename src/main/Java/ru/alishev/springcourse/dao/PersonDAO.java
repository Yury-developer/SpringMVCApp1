package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.util.List;


/*
 В уроке №26
  1. реализуеем метод save(Person person) с использованием PreparedStatement  (избежим SQL иньекций)
  2. реализуем оставшиеся методы в DAO
 */
@Component
public class PersonDAO {

    // Spring Framework. Урок 27: JdbcTemplate.
    private final JdbcTemplate jdbcTemplate;

    // Spring Framework. Урок 27: JdbcTemplate.
    @Autowired   // внедрим поле 'jdbcTemplate' используя внедрение с помощью конструктора
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /*
    На этом уроке № 27 мы полностью удаляем весь код, для подключения DB, т.к. делаем это в классе 'SpringConfig' используя метод 'jdbcTemplate()'
     */


    // На этом уроке № 27 мы полностью переписали весь код метода
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        /*
        1-й аргумент это наш SQL -запрос 'SELECT * FROM Person';
        2-й аргумент это - RowMapper ' new BeanPropertyRowMapper<>(Person.class)'.
           RowMapper - это такой объект,который отображает строки из таблицы в наши сущности,
           т.е. каждую строку, полученную в результате этого запроса из таблицы 'Person'
           он отобразит в объект класса 'Person'.

           Этот   BeanPropertyRowMapper   уже реализован, стандартный класс.
           'Person.class'   - тот класс, К ОБЪЕКТАМ КОТОРОГО БУДЕТ ПРОИЗВОДИТЬСЯ ПЕРЕВОД СТРОК ИЗ НАШЕЙ ТАБЛИЦЫ
         */
    }


    // На этом уроке № 27 мы переписали этот метод используя наш JdbcTemplate
    public void save(Person person) {   // ничего не возвращаем.
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)",
                // далее мы вставляем массив из ЛЮБОГО количества элементов (Object... args) т.е. используем   varargs
                person.getName(),
                person.getAge(),
                person.getEmail());
    }


    // На этом уроке № 27 мы переписали этот метод используя наш JdbcTemplate
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        /*
        В   jdbcTemplate   всегда по умолчаню используется PreparedStatement.
        1-й аргумент: SQL -запрос 'ELECT * FROM Person WHERE id=?'
        2-й аргумент: массив из значений, которые будут подставлены в наш PreparedStatement в качестве '?'
        3-й аргумент: такой-же   PersonMapper   какой использовали в методе   index()
           '.stream().findAny().orElse(null)'   ищет объект в списке,
           если объект найден - то он будет возвращен, если нет - то вернется   null
           В реальном приложении вернем не   null   . а объект, который вернет строку с названием ошибки
           например:   '.stream().findAny().orElse(new Error("Человек с таким id не найден."))'

           .findAny() возвращает объект типа 'Optional<T>' - это такой объект, кот. инкапсулирует то,
           что он может существовать, а может не существовать. Т.е. в этом объекте либо чежит (человек), либо ничего не лежит.
           соответственно в   .orElse   указываем что возвращаем, если там ничего не лежит.
         */
    }


    // На этом уроке № 27 мы переписали этот метод используя наш JdbcTemplate
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                // далее мы вставляем массив из ЛЮБОГО количества элементов (Object... args) т.е. используем   varargs
                updatedPerson.getName(),
                updatedPerson.getAge(),
                updatedPerson.getEmail(),
                id);
    }


    // На этом уроке № 27 мы переписали этот метод используя наш JdbcTemplate
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?",
                id);
    }

}
