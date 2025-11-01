package com.studentManagementSystem.studentManagement.service;

import com.studentManagementSystem.studentManagement.dto.CreateStudentRequestDTO;
import com.studentManagementSystem.studentManagement.dto.StudentDTO;
import com.studentManagementSystem.studentManagement.entity.Student;
import com.studentManagementSystem.studentManagement.entity.User;
import com.studentManagementSystem.studentManagement.exception.StudentAlreadyExistsException;
import com.studentManagementSystem.studentManagement.exception.StudentNotFoundException;
import com.studentManagementSystem.studentManagement.mapper.StudentMapper;
import com.studentManagementSystem.studentManagement.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserService userService; // Inject the new UserService

    /**
     * Creates a new student and a corresponding user account.
     * The username for the account will be the student's name.
     *
     * @param createStudentRequest The DTO containing student data and an initial password.
     * @return The DTO of the newly created student.
     */
    @Transactional
    public StudentDTO createStudent(CreateStudentRequestDTO createStudentRequest) {
        // First, check if a student with this email already exists.
        studentRepository.findByEmailId(createStudentRequest.getEmailId()).ifPresent(s -> {
            throw new StudentAlreadyExistsException("Student with email " + createStudentRequest.getEmailId() + " already exists.");
        });

        // 1. Create the User account first, using the student's name as the username.
        // This will throw an exception if the username is already taken.
        User createdUser = userService.createUser(
                createStudentRequest.getStudentName(),
                createStudentRequest.getPassword(),
                "ROLE_STUDENT" // Assign the default role for new students
        );

        // 2. Create the Student entity and link it to the new User account.
        Student studentToCreate = StudentMapper.toStudentEntity(createStudentRequest);
        studentToCreate.setUser(createdUser);

        // 3. Save the new Student, which now has the user association.
        Student createdStudent = studentRepository.save(studentToCreate);

        // 4. Return the DTO of the created student.
        return StudentMapper.toStudentDTO(createdStudent);
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAllWithDetails().stream()
                .map(StudentMapper::toStudentDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long studentId) {
        return studentRepository.findByIdWithDetails(studentId)
                .map(StudentMapper::toStudentDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
    }

    @Transactional
    public void deleteStudentById(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student not found with id: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public StudentDTO updateStudentById(Long studentId, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        // Apply updates from DTO to the existing entity
        existingStudent.setStudentName(studentDTO.getStudentName());
        existingStudent.setEmailId(studentDTO.getEmailId());
        existingStudent.setDateOfBirth(studentDTO.getDateOfBirth());

        Student updatedStudent = studentRepository.save(existingStudent);
        return StudentMapper.toStudentDTO(updatedStudent);
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudentByEmail(String emailId) {
        return studentRepository.findByEmailIdWithDetails(emailId)
                .map(StudentMapper::toStudentDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with email: " + emailId));
    }
}