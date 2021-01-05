package com.dawist_o.controllers;


import com.dawist_o.Service.AuthorService.AuthorService;
import com.dawist_o.Service.BookService.BookService;
import com.dawist_o.model.Author;
import com.dawist_o.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BooksController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("title", "Books");
        List<Book> bookList = bookService.getAll();
        Collections.reverse(bookList);
        model.addAttribute("books", bookList);
        return "books/books";
    }

    @GetMapping("/adding_book")
    public String addingBook(Model model) {
        model.addAttribute("title", "Adding book");
        List<Author> all = authorService.getAll();
        model.addAttribute("authors", all);
        return "books/adding_book";
    }

    @PostMapping("/adding_book")
    public String addingBookPost(@RequestParam String title, @RequestParam String author
            , @RequestParam String resume, @RequestParam String fullText
            , Model model) {

        Author authorByName = authorService.getByName(author);
        Book newBook;

        if (authorByName == null) {
            authorByName = new Author(author.trim(), "");
            authorService.save(authorByName);
        }

        newBook = new Book(authorByName, fullText, title, resume, 0);
        authorByName.addBook(newBook);
        bookService.save(newBook);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}")
    public String bookInfo(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsById(id)) return "redirect:/books";

        Book bookForInfo = bookService.getById(id);
        bookForInfo.setViews(bookForInfo.getViews() + 1);
        bookService.save(bookForInfo);
        model.addAttribute("book", bookForInfo);
        return "books/book_info";
    }

    @PostMapping("/book/{id}/delete")
    public String bookDelete(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsById(id)) return "redirect:/books";

        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String bookEdit(@PathVariable(value = "id") long id, Model model) {
        if (!bookService.existsById(id)) return "redirect:/books";
        Book bookForEdit = bookService.getById(id);
        model.addAttribute("book", bookForEdit);
        model.addAttribute("title", "Book editing");
        return "books/book_edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookEditPost(@RequestParam String title, @RequestParam String author,
                               @RequestParam String resume, @RequestParam String fullText,
                               @PathVariable(value = "id") long id, Model model) {
        Book editedBook = bookService.getById(id);
        editedBook.setTitle(title);
        //TODO realise front-end part to change author
        //editedBook.setAuthor(author);
        editedBook.setResume(resume);
        editedBook.setFulltext(fullText);
        bookService.save(editedBook);
        return "redirect:/book/" + id;
    }
}