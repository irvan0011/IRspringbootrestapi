package com.juaracoding.IRspringbootrestapi.repo;

import com.juaracoding.IRspringbootrestapi.Model.Person;
import com.juaracoding.IRspringbootrestapi.Model.Peserta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person,Integer> {
    List<Person> findByNamaNot(String nama); //Statrswith digunakan ketika kita ingin menggunakan query like=%..

}
