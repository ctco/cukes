package lv.ctco.cukes.rest.gadgets.dto;

import java.util.List;

public class Owner {
    private String name;
    private String surname;
    private Integer age;
    private List<String> roles;

    public Owner() {
    }

    public Owner(String name, String surname, Integer age, List<String> roles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.roles = roles;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
