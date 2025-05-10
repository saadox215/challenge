package org.example.challenge.services;

import org.example.challenge.entities.Course;
import org.example.challenge.repositories.Courserepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final Courserepository courseRepository;
    public CourseService(Courserepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public void addCourse(Course course) {
        courseRepository.save(course);
    }
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElse(null);
    }
    public Course updateCourse(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            course.setId(id);
            return courseRepository.save(course);
        }
        return null;
    }
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByEnseignant(Long id) {
        return courseRepository.findByEnseignantId(id);
    }
}
