package ru.job4j.dream.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String nameVacancy;
    private String name;
    private String secondName;

    public Candidate(int id, String nameVacancy, String name, String secondName) {
        this.id = id;
        this.nameVacancy = nameVacancy;
        this.name = name;
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameVacancy() {
        return nameVacancy;
    }

    public void setNameVacancy(String nameVacancy) {
        this.nameVacancy = nameVacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id && Objects.equals(nameVacancy, candidate.nameVacancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameVacancy);
    }
}
