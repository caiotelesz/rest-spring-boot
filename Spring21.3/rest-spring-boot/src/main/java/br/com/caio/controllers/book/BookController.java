package br.com.caio.controllers.book;

import br.com.caio.controllers.book.docs.BookControllerDocs;
import br.com.caio.data.dto.book.BookDTO;
import br.com.caio.services.book.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookServices bookServices;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public List<BookDTO> findAll() {
        return bookServices.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    @Override
    public BookDTO findById(@PathVariable Long id) {
        return bookServices.findById(id);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    @Override
    public BookDTO createBook(@RequestBody BookDTO book) {
        return bookServices.createBook(book);
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    @Override
    public BookDTO updateBook(@RequestBody BookDTO book) {
        return bookServices.updateBook(book);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> deleteBookPerId(@PathVariable("id") Long id) {
        bookServices.deleteBookPerId(id);
        return ResponseEntity.noContent().build();
    }
}
