package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
 В этом уроке №26
  1. реализуеем метод save(Person person) с использованием PreparedStatement  (избежим SQL иньекций)
  2. реализуем оставшиеся методы в DAO
 */
@Component
public class PersonDAO {
//    private static int PEOPLE_COUNT; // счетчик людей для формирования 'id'

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
    На этом уроке № 26 мы не будем трогать этот метод, т.к. тут статический SQL. мы не берем данные с формы (от пользователя)
    В остальных методах DAO будем использовать PreparedStatement
     */
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


    // На этом уроке № 26 мы переписали этот метод.
    public void save(Person person) {   // ничего не возвращаем.
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");  // далее вместо '1' будем автоматически генерировать id

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();  // выполняет запрос к BD (изменяе ее состоние)

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // На этом уроке № 26 мы переписали этот метод.
    public Person show(int id) {
        Person person = null;   // создадим указатель

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();   // по данному запросу вернулись ВСЕ строки, которые были получены по этому запросу из DB

            resultSet.next();   // запросим ТОЛЬКО ПЕРВУЮ строчку из ResultSet
            // Если таких людей с указанным id будет несколько, то возтмем только первого, который вернулся

            person = new Person();   // поместим эту строчку в объект класса Person

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }


    // На этом уроке № 26 мы переписали этот метод.
    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);   // id того чел. кот-го мы будем обновлять
            // Если в табл. будет несколоько чел. с одинаковыми id, то у всех их будут обновлены значения в заданных колонках

            preparedStatement.executeUpdate();   // Если не выполнить executeUpdate, то созданный запрос не будет выполнен!

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // На этом уроке № 26 мы переписали этот метод.
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();   // Если не выполнить executeUpdate, то созданный запрос не будет выполнен!

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
