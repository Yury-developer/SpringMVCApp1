package ru.alishev.springcourse.models;


import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;

    /*
    Урок № 24:
    С помощью 'org.hibernate.validator' (см. README.md) аннотациями
    прямо на самих полях сможем устанавливать правила валидации
     */

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")   // от 2 до 30 букв
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")   // минимальный возраст 0, иначе сообщение об ошибке
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")   // аннотация проверяет, что в поле именно emaal
    private String email;


    public Person() {
        // NOP
    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
