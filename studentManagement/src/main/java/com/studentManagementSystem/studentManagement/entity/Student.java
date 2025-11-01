package com.studentManagementSystem.studentManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String studentName;
    @Column(unique = true, nullable = false)
    private String emailId;
    private LocalDate dateOfBirth;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime studentRegistered;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    /**
     * The user account associated with this student.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Student() {
    }

    public Student(String studentName, String emailId, LocalDate dateOfBirth, User user) {
        this.studentName = studentName;
        this.emailId = emailId;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getStudentRegistered() {
        return studentRegistered;
    }

    public void setStudentRegistered(LocalDateTime studentRegistered) {
        this.studentRegistered = studentRegistered;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Helper method to add an enrollment for this student.
     * It ensures that the bidirectional relationship is maintained correctly.
     *
     * @param enrollment The enrollment to add.
     */
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    /**
     * Helper method to remove an enrollment for this student.
     * It ensures that the bidirectional relationship is maintained correctly.
     *
     * @param enrollment The enrollment to remove.
     */
    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }

}