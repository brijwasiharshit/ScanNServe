package com.app.ScanNServe;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class PersonController {

    private final PersonRepository personRepository;


    @PostMapping("/add")
    public Person add() {
        Person person = new Person();
        person.setName("Saurabh");
        return  personRepository.save(person);


    }
}
