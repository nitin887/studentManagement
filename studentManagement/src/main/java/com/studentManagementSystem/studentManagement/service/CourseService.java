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


public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO);
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long courseId);
    void deleteCourseById(Long courseId);
    CourseDTO updateCourseById(Long courseId, CourseDTO courseDTO);
}