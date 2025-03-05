package br.com.caio.controllers.book;

import br.com.caio.data.dto.book.BookDTO;
import br.com.caio.services.book.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public List<BookDTO> findAll() {
        return bookServices.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BookDTO findById(@PathVariable Long id) {
        return bookServices.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BookDTO save(@RequestBody BookDTO book) {
        return bookServices.createBook(book);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BookDTO update(@RequestBody BookDTO book) {
        return bookServices.updateBook(book);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        bookServices.deleteBookPerId(id);
        return ResponseEntity.noContent().build();
    }
}
