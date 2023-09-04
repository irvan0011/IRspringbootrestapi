package com.juaracoding.IRspringbootrestapi.Controlller;

import com.juaracoding.IRspringbootrestapi.Model.Person;
import com.juaracoding.IRspringbootrestapi.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    PersonRepo personRepo;

    @Autowired
    public PersonController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }
    @GetMapping("/getPerson")
    public List<Person> getPerson(Person person){
        List<Person> hasil= personRepo.findAll();
        return hasil;
    }
    @GetMapping("/getPerson-sw")
    public List<Person> getPersonsw(@RequestParam(value = "nama") String nama){
        List<Person> hasil= personRepo.findByNamaNot(nama);
        return hasil;
    }
    @PostMapping("/postPerson")
    public String postPerson(@Valid @RequestBody Person person){
        personRepo.save(person);
        return person.getNama() + " berhasil ditambahkan";
    }

    @DeleteMapping("/deletePerson")
    public String deletePerson(@RequestParam(value = "id") Integer id){
        personRepo.deleteById(id);
        return "Data Berhasil Dihapus";
    }
}
