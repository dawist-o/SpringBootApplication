package com.dawist_o.controllers;


import com.dawist_o.service.BookService.BookService;
import com.dawist_o.model.Author;
import com.dawist_o.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path="/book")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/all")
    public String books(Model model) {
        model.addAttribute("title", "Books");
        List<Book> bookList = bookService.getAllBooks();
        Collections.reverse(bookList);
        model.addAttribute("books", bookList);
        return "books/books";
    }

    @GetMapping("/add")
    public String addingBook(Model model) {
        model.addAttribute("title", "Adding book");
        return "books/add_book";
    }

    @PostMapping("/add")
    public String addingBookPost(@RequestParam String title, @RequestParam String author,
                                 @RequestParam String resume, @RequestParam String fullText,
                                 Model model) {

        Author authorByName = bookService.getAuthorByNameOrCreateNew(author);
        Book newBook = new Book(authorByName, fullText, title, resume, 0);
        authorByName.addBook(newBook);
        bookService.save(newBook);
        return "redirect:/book/all";
    }

    @GetMapping("/{id}")
    public String bookInfo(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsBookById(id)) return "redirect:/books";

        Book bookForInfo = bookService.getBookById(id);
        bookForInfo.setViews(bookForInfo.getViews() + 1);
        bookService.save(bookForInfo);
        model.addAttribute("book", bookForInfo);
        return "books/book_info";
    }

    @PostMapping("/{id}/delete")
    public String bookDelete(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsBookById(id)) return "redirect:/books";

        bookService.deleteBookById(id);
        return "redirect:/all";
    }

    @GetMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String bookEdit(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsBookById(id)) return "redirect:/books";
        Book bookForEdit = bookService.getBookById(id);
        model.addAttribute("book", bookForEdit);
        model.addAttribute("title", "Book editing");
        return "books/book_edit";
    }

    @PostMapping("/{id}/edit")
    public String bookEditPost(@RequestParam String title, @RequestParam String author,
                               @RequestParam String resume, @RequestParam String fullText,
                               @PathVariable(value = "id") long id, Model model) {
        Book editedBook = bookService.getBookById(id);
        editedBook.setTitle(title);
        //TODO realise front-end part to change author
        //editedBook.setAuthor(author);
        editedBook.setResume(resume);
        editedBook.setFulltext(fullText);
        bookService.save(editedBook);
        return "redirect:/book/" + id;
    }
}