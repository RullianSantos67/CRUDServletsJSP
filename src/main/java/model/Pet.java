package model;

import java.sql.Date;

public class Pet {
    private int id;
    private String name;
    private String species;
    private Date birthdate;
    private float weight;
    private int ownerId;

    public Pet() {}

    public Pet(int id, String name, String species, Date birthdate, float weight, int ownerId) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.birthdate = birthdate;
        this.weight = weight;
        this.ownerId = ownerId;
    }

    public Pet(String name, String species, Date birthdate, float weight, int ownerId) {
        this.name = name;
        this.species = species;
        this.birthdate = birthdate;
        this.weight = weight;
        this.ownerId = ownerId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
}
