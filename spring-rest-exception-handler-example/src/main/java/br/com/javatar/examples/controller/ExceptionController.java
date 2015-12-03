package br.com.javatar.examples.controller;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.javatar.examples.dto.ExampleDto;

@RestController
@RequestMapping(value = "/exceptions")
public class ExceptionController {

    private static final Validator VALIDATOR = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory().getValidator();

    @RequestMapping(method = RequestMethod.POST, value = "/415", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Void> throw415() {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/406", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Void> throw406() {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/404", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Void> throw404() {
        String nullPointer = null;
        nullPointer.toString();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/405")
    @ResponseBody
    public ResponseEntity<Void> throw405() {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/422", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Void> throw422(@Valid ExampleDto exampleDto) {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}