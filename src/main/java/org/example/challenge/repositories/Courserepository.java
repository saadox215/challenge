package org.example.challenge.repositories;

import org.example.challenge.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Courserepository extends JpaRepository<Course, Long> {
    List<Course> findByEnseignantId(Long id);
}
