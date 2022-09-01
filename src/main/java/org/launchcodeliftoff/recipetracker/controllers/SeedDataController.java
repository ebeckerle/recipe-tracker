package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.models.RecipeData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seed-data")
public class SeedDataController {

    @GetMapping
    public String loadSeedData(Model model){
        model.addAttribute("recipes", RecipeData.findAll());
        return "seed-data";
    }
}
