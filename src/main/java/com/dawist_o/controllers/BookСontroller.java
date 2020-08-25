package com.dawist_o.controllers;


import com.dawist_o.model.Book;
import com.dawist_o.repository.BookRepo;
import lombok.Lombok;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BookСontroller {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/books")
    public String blog(Model model) {
        model.addAttribute("title", "Books");
        Iterable<Book> books = bookRepo.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/adding_book")
    public String addingBook(Model model) {
        model.addAttribute("title", "Adding book");
        return "adding_book";
    }

    @PostMapping("/adding_book")
    public String addingBookPost(@RequestParam String title, @RequestParam String author,
                                 @RequestParam String resume, @RequestParam String fullText
            , Model model) {
        Book newBook = new Book(title, author, resume, fullText, 0);
        bookRepo.save(newBook);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}")
    public String bookInfo(@PathVariable(value = "id") long id, Model model) {
        if (!bookRepo.existsById(id)) return "redirect:/books";
        Book bookForInfo = bookRepo.findById(id).orElseThrow();
        //  ArrayList<Book> bookInfo = new ArrayList<>();
        // bookOptional.ifPresent(bookInfo::add);
        bookForInfo.setViews(bookForInfo.getViews()+1);
        bookRepo.save(bookForInfo);
        model.addAttribute("book", bookForInfo);
        return "book_info";
    }

    //can be made with GetMapping and /form in book_info.html file
    @PostMapping("/book/{id}/delete")
    public String bookDelete(@PathVariable(value = "id") long id, Model model) {
        if (!bookRepo.existsById(id)) return "redirect:/books";
        bookRepo.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value = "id") long id, Model model) {
        if (!bookRepo.existsById(id)) return "redirect:/books";
        Book bookForEdit = bookRepo.findById(id).orElseThrow();
        model.addAttribute("book", bookForEdit);
        model.addAttribute("title", "Book editing");
        return "book_edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookEditPost(@RequestParam String title, @RequestParam String author,
                               @RequestParam String resume, @RequestParam String fullText,
                               @PathVariable(value = "id") long id, Model model) {
        Book editedBook = bookRepo.findById(id).orElseThrow();
        editedBook.setTitle(title);
        editedBook.setAuthor(author);
        editedBook.setResume(resume);
        editedBook.setFullText(fullText);
        bookRepo.save(editedBook);
        return "redirect:/book/"+id;
    }
}

