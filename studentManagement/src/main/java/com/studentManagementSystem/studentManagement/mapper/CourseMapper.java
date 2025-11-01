package com.studentManagementSystem.studentManagement.mapper;

import com.studentManagementSystem.studentManagement.dto.CourseDTO;
import com.studentManagementSystem.studentManagement.entity.Course;

public final class CourseMapper {
    private CourseMapper() {}

    public static CourseDTO toCourseDTO(Course course){
        if(course == null){
            return  null;
        }
        return new CourseDTO(
                course.getCourseId(),
                course.getCourseName(),
                course.getCourseDescription()
        );
    }

    public static Course toCourseEntity(CourseDTO courseDTO){
        if (courseDTO == null) {
            return null;
        }
        Course course = new Course();
        // We don't set the ID, as it's generated
        course.setCourseName(courseDTO.getCourseName());
        course.setCourseDescription(courseDTO.getCourseDescription());
        return course;
    }
}