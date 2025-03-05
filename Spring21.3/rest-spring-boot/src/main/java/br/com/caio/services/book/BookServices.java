package br.com.caio.services.book;

import br.com.caio.controllers.book.BookController;
import br.com.caio.data.dto.book.BookDTO;
import br.com.caio.exception.RequiredObjectIsNullException;
import br.com.caio.exception.ResourceNotFoundException;
import br.com.caio.model.book.Book;
import br.com.caio.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static br.com.caio.mapper.ObjectMapper.parseListObjects;
import static br.com.caio.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    BookRepository bookRepository;

    public List<BookDTO> findAll() {
        logger.info("Find all Books!");

        var books = parseListObjects(bookRepository.findAll(), BookDTO.class);
        books.forEach(this::addHateaoasLinks);
        return books;
    }

    public BookDTO findById(Long id) {
        logger.info("Find one Book!");

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var dto = parseObject(entity, BookDTO.class);
        addHateaoasLinks(dto);
        return dto;
    }

    public BookDTO createBook(BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Create a new Book!");

        var entity = parseObject(book, Book.class);

        var dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateaoasLinks(dto);
        return dto;
    }

    public BookDTO updateBook(BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Book!");
        var entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateaoasLinks(dto);
        return dto;
    }

    public void deleteBookPerId(Long id) {
        logger.info("Deleting one Book!");

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        bookRepository.delete(entity);
    }

    private void addHateaoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).save(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
