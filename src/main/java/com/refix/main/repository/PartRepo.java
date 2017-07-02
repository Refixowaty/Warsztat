package com.refix.main.repository;


import com.refix.main.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartRepo extends JpaRepository<Part, Long>{

    public Part findByName(String name);

    @Query(value = "SELECT * FROM Part part WHERE part.quantity > 0", nativeQuery = true)
    public List<Part> findAllWhereQuantityMoreThanZero();
}
