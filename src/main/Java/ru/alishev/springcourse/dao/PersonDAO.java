package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
 В этом уроке №25 подключим базу данных DB: PostgreSQL
 */
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT; // счетчик людей для формирования 'id'

    /*
    Эти данные обычно храняться в отдельном файле .properties
    В экспериментальных целях расположим данные доступа к DB в статических переменных.
     */
    private static final String URL = "jdbc:postgresql://localhost:5434/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;   // соединение с помощью JDBC с нашей DB

    static {   // инициализируем нашу статическую переменную в статическом блоке
        try {
            Class.forName("org.postgresql.Driver");   // подгружаем класс с нашим JDBC - драйвером
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException throwables) {
                // проблема работы с JDBC:
                // 1. Очень низкоуровневый, все команды мы вынуждены писать вручную;
                // 2.любая ошибка при работе с DB - это SQLException,
                // т.е. мы не можем понять какая именно ошибка произошла (добавление, удаление...)
            throwables.printStackTrace();
        }
    }


    /*
    На этом уроке № 25 мы реализуем 2 метода:
       index()   - будет возвращать всех людей, будет брать из из базы данных и возвращать их в представление.
       save(Person person)   - будет записывать нового человека в нашу базу данных
     */
    // просто возвращает список из людей, который потом с помощью thymeleaf мы отобразим в браузере
    public List<Person> index() {
        List<Person> people = new ArrayList<>();   // будем сюда ложить людей, которых будем брать из DB

        try {
            Statement statement = connection.createStatement(); // тот объект, который содержит в себе сам запрос с DB

            String SQL = "SELECT * FROM Person";   // наш SQL -запрос, кот. будет делаться на нашем DAO
            ResultSet resultSet = statement.executeQuery(SQL);  // выполняет запрос к DB (НЕ изм. ее сост.) и возвращает какаие-то данные..
            /*
            выполняет наш запрос, возвращаются строки, помещаем их в ResultSet -это результат обращения к DB.
            Вернутся строки. Теперь нужно пройтись по всем этим строкам и вручную их перевести в Java - объекты
             */

            while(resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    public void save(Person person) {   // ничего не возвращаем.
//        person.setId(++PEOPLE_COUNT);   // id будем назначать динамически, т.е. пользователь не будет его вводить вручную!
//        people.add(person);
        try {
            Statement statement = connection.createStatement();
            // В эту строку нужно внедрять значения из объекта 'Person'. Это очень черевато и так делать нехорошо
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);  // выполняет запрос к BD (изменяе ее состоние) и никаких данных не возвращает!
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    // на этом уроке № 25 этот метод закоментим, трогать его не будем.
    // возвращает 1-го человека по id
    public Person show(int id) {
//        // находим человека с этим id, если такого человека нет - то возвращаем null
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return null;
    }



    // на этом уроке № 25 этот метод закоментим, трогать его не будем.
    public void update(int id, Person updatedPerson) {   // 'id' -id существующего человека; 'updatedPerson' -новые значения, кот. мы получили сч формы редактирования человека
//        Person personToBeUpdated = show(id);   // находим 'человека' которого необходимо обновить. Если бы было несколько полей, то обновили бы все поля.
//        personToBeUpdated.setName(updatedPerson.getName());   // обновляем у него поле 'name' тем полем, которое пришло из формы редактирования
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    // на этом уроке № 25 этот метод закоментим, трогать его не будем.
    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);   // в ArrayList есть метод удаления по предикату, если   '(p -> p.getId() == id)'   true -> то элемент будет удален из нашего списка
        // проходимся по каждому человеку из нашего списка, у него вызываем '.getId()' и если id равны, то удаляем
    }

}
