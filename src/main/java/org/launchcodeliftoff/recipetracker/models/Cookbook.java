package org.launchcodeliftoff.recipetracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cookbook {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany
    private List<Recipe> recipes = new ArrayList<>();

    public Cookbook() {
    }

    public Cookbook(String name) {
        this.name = name;
    }

    // GETTERS & SETTERS

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
