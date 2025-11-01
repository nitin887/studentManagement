package com.studentManagementSystem.studentManagement.mapper;

import com.studentManagementSystem.studentManagement.dto.EnrollmentDTO;
import com.studentManagementSystem.studentManagement.dto.StudentDTO;
import com.studentManagementSystem.studentManagement.entity.Enrollment;

public final class EnrollmentMapper {
    private EnrollmentMapper() {}

    public static EnrollmentDTO toEnrollmentDTO(Enrollment enrollment){
        if(enrollment == null){
            return  null;
        }

        // Create a simplified StudentDTO to avoid the recursive call to map enrollments.
        StudentDTO studentDTO = null;
        if (enrollment.getStudent() != null) {
            studentDTO = new StudentDTO();
            studentDTO.setStudentId(enrollment.getStudent().getStudentId());
            studentDTO.setStudentName(enrollment.getStudent().getStudentName());
            studentDTO.setEmailId(enrollment.getStudent().getEmailId());
            studentDTO.setDateOfBirth(enrollment.getStudent().getDateOfBirth());
            studentDTO.setStudentRegistered(enrollment.getStudent().getStudentRegistered());
            // DO NOT set the enrollments list here.
        }

        return new EnrollmentDTO(
                enrollment.getEnrollmentId(),
                enrollment.getEnrollmentDate(),
                CourseMapper.toCourseDTO(enrollment.getCourse()),
                studentDTO
        );
    }
}