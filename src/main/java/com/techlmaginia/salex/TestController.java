package com.techlmaginia.salex;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TestController {
    @PostMapping(value = "/testhi",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test()  {
        return new ResponseEntity<String>("hi", HttpStatus.OK);
    }

}
