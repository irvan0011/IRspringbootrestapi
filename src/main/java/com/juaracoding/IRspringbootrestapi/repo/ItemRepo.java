package com.juaracoding.IRspringbootrestapi.repo;

import com.juaracoding.IRspringbootrestapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Long> {
}
