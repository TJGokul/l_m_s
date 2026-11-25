package com.example.library_management_system.controller;
import com.example.library_management_system.entity.Book;
import com.example.library_management_system.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/books")
@CrossOrigin(origins="*")
public class BookController {
    private final BookService service; public BookController(BookService s){service=s;}
    @GetMapping public List<Book> all(@RequestParam(required=false) String search){return service.all(search);}
    @GetMapping("/{id}") public Book one(@PathVariable Long id){return service.one(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Book create(@Valid @RequestBody Book b){return service.create(b);}
    @PutMapping("/{id}") public Book update(@PathVariable Long id,@Valid @RequestBody Book b){return service.update(id,b);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
}