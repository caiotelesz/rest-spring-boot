package br.com.caio.services;

import br.com.caio.controllers.PersonController;
import br.com.caio.data.dto.v1.PersonDTO;
import br.com.caio.exception.RequiredObjectIsNullException;
import br.com.caio.exception.ResourceNotFoundException;
import br.com.caio.model.Person;
import br.com.caio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.caio.mapper.ObjectMapper.parseListObjects;
import static br.com.caio.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {
        logger.info("Finding all People!");

        var people = parseListObjects(repository.findAll(), PersonDTO.class);
        people.forEach(this::addHateaoasLinks);
        return people;
    }

    
    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var dto = parseObject(entity, PersonDTO.class);
        addHateaoasLinks(dto);
        return dto;
    }

    public PersonDTO createPerson(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");

        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateaoasLinks(dto);
        return dto;
    }

    public PersonDTO updatePerson(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateaoasLinks(dto);
        return dto;
    }

    public void deletePersonById(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

    private void addHateaoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET")); // findById
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE")); // delete
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET")); // findAll
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST")); // create
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT")); // update
    }
}