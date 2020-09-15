package com.dawist_o.controllers;


import com.dawist_o.Service.BookService;
import com.dawist_o.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BookController{

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String blog(Model model) {
        model.addAttribute("title", "Books");
        List<Book> bookList=bookService.getAll();
        Collections.reverse(bookList);
        model.addAttribute("books", bookList);
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
        bookService.save(newBook);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}")
    public String bookInfo(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsById(id)) return "redirect:/books";
        Book bookForInfo = bookService.getById(id);
        bookForInfo.setViews(bookForInfo.getViews()+1);
        bookService.save(bookForInfo);
        model.addAttribute("book", bookForInfo);
        return "book_info";
    }

    @PostMapping("/book/{id}/delete")
    public String bookDelete(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsById(id)) return "redirect:/books";
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsById(id)) return "redirect:/books";
        Book bookForEdit = bookService.getById(id);
        model.addAttribute("book", bookForEdit);
        model.addAttribute("title", "Book editing");
        return "book_edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookEditPost(@RequestParam String title, @RequestParam String author,
                               @RequestParam String resume, @RequestParam String fullText,
                               @PathVariable(value = "id") long id, Model model) {
        Book editedBook = bookService.getById(id);
        editedBook.setTitle(title);
        editedBook.setAuthor(author);
        editedBook.setResume(resume);
        editedBook.setFullText(fullText);
        bookService.save(editedBook);
        return "redirect:/book/"+id;
    }
}

