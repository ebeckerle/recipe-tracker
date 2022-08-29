package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.CommentRepository;
import org.launchcodeliftoff.recipetracker.data.CookbookRepository;
import org.launchcodeliftoff.recipetracker.data.RecipeRepository;
import org.launchcodeliftoff.recipetracker.data.UserRepository;
import org.launchcodeliftoff.recipetracker.models.Comment;
import org.launchcodeliftoff.recipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
//TODO: requestmapping will probably need to change before / after merged with main
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String displayAddRecipe(Model model){
        model.addAttribute("title", "Add A Recipe");
        model.addAttribute(new Recipe());
        return "add-recipe";
    }

    @PostMapping("/add")
    public String processAddRecipe(@ModelAttribute @Valid Recipe newRecipe, Errors errors,
//                                   HttpServletRequest request,
                                   Model model){
        if (errors.hasErrors()){
            return "add-recipe";
        }

//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("user");
//        newRecipe.setRecipeAuthor(userRepository.findById(userId));

        recipeRepository.save(newRecipe);
        model.addAttribute("recipe", recipeRepository.findById(newRecipe.getId()).get());
        model.addAttribute("title", recipeRepository.findById(newRecipe.getId()).get().getName());
        model.addAttribute(new Comment());
        return "view-recipe";
    }

    @GetMapping("/{recipeId}")
    public String displayViewRecipe(Model model, @PathVariable Integer recipeId){
        model.addAttribute("title", recipeRepository.findById(recipeId).get().getName());
        model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
        model.addAttribute("comments", commentRepository.findByRecipeId(recipeId));

        model.addAttribute(new Comment());
        return "view-recipe";
    }

    @PostMapping("/{recipeId}")
    public String processAddCommentForm(Comment newComment,
//                                      HttpServletRequest request,
                                        Model model, @PathVariable Integer recipeId
                                        ){

//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("user");
//        newComment.setUser(userRepository.findById(userId));

        newComment.setRecipe(recipeRepository.findById(recipeId).get());
        commentRepository.save(newComment);

        model.addAttribute("title", recipeRepository.findById(recipeId).get().getName());
        model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
        model.addAttribute("comments", commentRepository.findByRecipeId(recipeId));
        model.addAttribute(new Comment());

        return "view-recipe";
    }

}
