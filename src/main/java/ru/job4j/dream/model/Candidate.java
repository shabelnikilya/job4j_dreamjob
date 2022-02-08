package ru.job4j.dream.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private City cityId;
    private String nameVacancy;
    private String name;
    private String secondName;
    private LocalDateTime createdCandidate;

    public Candidate(int id, String nameVacancy, String name, String secondName) {
        this.id = id;
        this.nameVacancy = nameVacancy;
        this.name = name;
        this.secondName = secondName;
    }

    public Candidate(int id, City cityId, String nameVacancy, String name, String secondName) {
        this(id, nameVacancy, name, secondName);
        this.cityId = cityId;
    }

    public Candidate(
            int id, City cityId, String nameVacancy, String name, String secondName, LocalDateTime createdCandidate) {
        this(id, cityId, nameVacancy, name, secondName);
        this.createdCandidate = createdCandidate;
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

    public City getCity() {
        return this.cityId;
    }

    public String getCityName() {
        return cityId.getName();
    }

    public void setCity(City city) {
        this.cityId = city;
    }

    public LocalDateTime getCreatedCandidate() {
        return createdCandidate;
    }

    public void setCreatedCandidate(LocalDateTime createdCandidate) {
        this.createdCandidate = createdCandidate;
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

    @Override
    public String toString() {
        return "Candidate{" + "id=" + id
                + ", cityId=" + cityId + ", nameVacancy='"
                + nameVacancy + '\'' + ", name='" + name + '\''
                + ", secondName='" + secondName + '\'' + '}';
    }
}
