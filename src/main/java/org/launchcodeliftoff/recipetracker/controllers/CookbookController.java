package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.CookbookRepository;
import org.launchcodeliftoff.recipetracker.data.RecipeRepository;
import org.launchcodeliftoff.recipetracker.data.UserRepository;
import org.launchcodeliftoff.recipetracker.models.Cookbook;
import org.launchcodeliftoff.recipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cookbook")
public class CookbookController {

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/add")
    public String displayAddCookbookForm(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");

        model.addAttribute("title", "Create A Cookbook");
        model.addAttribute(new Cookbook());
        List<Recipe> recipes = new ArrayList<>();
        List<Recipe> myRecipes = (List<Recipe>) recipeRepository.findByRecipeAuthorId(userId);
        List<Recipe> savedRecipes = userRepository.findById(userId).get().getSavedRecipes();
        recipes.addAll(myRecipes);
        recipes.addAll(savedRecipes);
        model.addAttribute("recipes", recipes);
        return "add-cookbook";
    }

    @PostMapping("/add")
    public String processAddCookbookForm(@ModelAttribute @Valid Cookbook newCookbook, Errors errors,
                                   HttpServletRequest request,
                                         Model model){
        if (errors.hasErrors()){
            return "add-recipe";
        }

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        newCookbook.setUser(userRepository.findById(userId).get());

        cookbookRepository.save(newCookbook);
        return "view-cookbook";
    }

    //will render at "/cookbook/view" and will display list all of the logged in user's cookbooks
    @GetMapping("/view")
    public String displayAllCookbooks(HttpServletRequest request, Model model){
        //get user in session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        System.out.println("we are in the displayAllCookbooks");
        //add a list of cookbooks attached to the user to the model
        model.addAttribute("cookbooks", cookbookRepository.findByUserId(userId));
        return "view-cookbooks";
    }

    @GetMapping("/view/{cookbookId}")
    public String displayViewCookbook(@PathVariable Integer cookbookId, Model model){
        model.addAttribute("cookbook", cookbookRepository.findById(cookbookId).get());
        return "view-cookbook";
    }
}
