package com.example.Guide.Guide.repository;

import com.example.Guide.Guide.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide,Long> {
    List<Guide> findByFamousPlace(String famousPlace);

}
