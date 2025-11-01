package com.studentManagementSystem.studentManagement.service;

import com.studentManagementSystem.studentManagement.dto.CourseDTO;
import com.studentManagementSystem.studentManagement.entity.Course;
import com.studentManagementSystem.studentManagement.exception.CourseAlreadyExistsException;
import com.studentManagementSystem.studentManagement.exception.CourseNotFoundException;
import com.studentManagementSystem.studentManagement.mapper.CourseMapper;
import com.studentManagementSystem.studentManagement.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO) {
        courseRepository.findByCourseName(courseDTO.getCourseName()).ifPresent(c -> {
            throw new CourseAlreadyExistsException("Course with name '" + courseDTO.getCourseName() + "' already exists.");
        });
        Course courseToCreate = CourseMapper.toCourseEntity(courseDTO);
        Course createdCourse = courseRepository.save(courseToCreate);
        return CourseMapper.toCourseDTO(createdCourse);
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(CourseMapper::toCourseDTO).toList();
    }

    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long courseId) {

        return courseRepository.findById(courseId)
                .map(CourseMapper::toCourseDTO)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
    }

    @Transactional
    public void deleteCourseById(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public CourseDTO updateCourseById(Long courseId, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        // Update with new data
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setCourseDescription(courseDTO.getCourseDescription());

        Course updatedCourse = courseRepository.save(existingCourse);
        return CourseMapper.toCourseDTO(updatedCourse);
    }
}