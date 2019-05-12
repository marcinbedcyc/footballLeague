package pl.football.league.entities;

import javax.persistence.*;

@Entity
@Table(name ="trener")
public class Coach {
    @Id
    @GeneratedValue
    @Column(name = "id_trenera")
    private long coachID;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column (name = "wiek", nullable = true)
    private Integer age;

    public Coach(){

    }

    public Coach(long id, String name, String surname, Integer age){
        coachID = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public long getCoachID() {
        return coachID;
    }

    public void setCoachID(long coachID) {
        this.coachID = coachID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
