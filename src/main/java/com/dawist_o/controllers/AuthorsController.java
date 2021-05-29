package com.dawist_o.controllers;


import com.dawist_o.service.AuthorService.AuthorService;
import com.dawist_o.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthorsController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("title", "Authors");
        List<Author> all = authorService.getAll();
        model.addAttribute("authors", all);
        return "authors/authors";
    }

    @GetMapping("/add_author")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addingAuthor(Model model) {
        model.addAttribute("title", "Adding author");
        return "authors/adding_author";
    }

    @PostMapping("/add_author")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addingAuthor(@RequestParam String name, @RequestParam String biography) {
        Author author = new Author(name, biography);
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/author/{id}")
    public String authorInfo(@PathVariable(value = "id") long id, Model model) {
        if (!authorService.existsById(id)) return "redirect:/authors";

        Author byId = authorService.getById(id);
        model.addAttribute("author", byId);
        model.addAttribute("books", byId.getBooks());
        return "authors/author_info";
    }

    @PostMapping("/author/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAuthor(@PathVariable(value = "id") long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

    @GetMapping("/author/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editAuthor(@PathVariable(value = "id") long id, Model model) {
        if (!authorService.existsById(id)) return "redirect:/authors";

        Author byId = authorService.getById(id);
        model.addAttribute("author", byId);
        model.addAttribute("title", "Author editing");
        return "authors/author_edit";
    }

    @PostMapping("/author/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editAuthorPost(@RequestParam String name, @RequestParam String biography,
                                 @PathVariable(value = "id") long id) {
        if (!authorService.existsById(id)) return "redirect:/authors";

        Author byId = authorService.getById(id);
        byId.setName(name);
        byId.setBiography(biography);
        authorService.save(byId);

        return "redirect:/author/" + id;
    }


}
