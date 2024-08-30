package org.example.controller;

import org.example.models.Author;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/{id}")
    public String getAuthorById(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "author";
    }

    @PostMapping
    public String saveAuthor(@ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @PutMapping("/{id}")
    public String updateAuthor(@PathVariable Integer id, @ModelAttribute Author author) {
        author.setId(id);
        authorService.updateAuthor(author);
        return "redirect:/authors";
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}