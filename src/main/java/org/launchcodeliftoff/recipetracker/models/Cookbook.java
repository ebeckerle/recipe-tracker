package org.launchcodeliftoff.recipetracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cookbook {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "Please name your Recipe!")
    private String name;

    @ManyToMany
    private List<Recipe> recipes = new ArrayList<>();

    @ManyToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
