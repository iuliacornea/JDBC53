package org.example.model;

// reprezentare a unei instanțe (un rând) din tabelul "animals"
public class Animal {

    private Integer id; // nu folosim int pentru a evita NullPointerException

    private String name;

    private String species;


    public Animal() {
    }

    public Animal(Integer id, String name, String species) {
        this.id = id;
        this.name = name;
        this.species = species;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
