package ru.alishev.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.alishev.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


/*
Spring Framework. Урок 27: JdbcTemplate.
Для создания   RowMapper   мы должны унаследоваться от   org.springframework.jdbc.core.RowMapper
 */
public class PersonMapper implements org.springframework.jdbc.core.RowMapper<Person> { // параметризуем, т.к. преобразуем строки таблицы в объекты класса '<Person>'

	  /*
	  В этом методе мы из нашего интерфейса должны брать очередную строку
	  и переводить ее в объект класса 'Person'.
	  т.е. должны делать то же самое, что мы делали в методе show(int id) класса PersonDAO.

	  Ниже мы вынесем весь код (чтобы не дублировался) по извлечению данных из таблицы
	  и перевода его в объекты класса 'Person' в ЭТОТ отдельный класс 'PersonMapper'
	   */
	  @Override
	  public Person mapRow(ResultSet resultSet, int i) throws SQLException {

			Person person = new Person();

			person.setId(resultSet.getInt("id"));
			person.setName(resultSet.getString("name"));
			person.setEmail(resultSet.getString("email"));
			person.setAge(resultSet.getInt("age"));

			return person;
	  }
}
