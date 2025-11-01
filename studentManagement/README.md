# Student Management System

This is a RESTful API for a Student Management System. It allows you to manage students, courses, and enrollments.

## Getting Started

To get the project running locally, you will need to have the following installed:

*   Java 17 or higher
*   Maven

Once you have the prerequisites, you can clone the repository and run the application using the following commands:

```bash
git clone https://github.com/your-username/student-management-system.git
cd student-management-system
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## API Endpoints

The following is a summary of the available API endpoints. For more detailed information, you can view the Swagger documentation at `http://localhost:8080/swagger-ui.html`.

### Students

*   `POST /api/students`: Create a new student.
*   `GET /api/students`: Get a list of all students.
*   `GET /api/students/{studentId}`: Get a student by their ID.
*   `GET /api/students/email/{emailId}`: Get a student by their email address.
*   `PUT /api/students/{studentId}`: Update an existing student.
*   `DELETE /api/students/{studentId}`: Delete a student.

### Courses

*   `POST /api/courses`: Create a new course.
*   `GET /api/courses`: Get a list of all courses.
*   `GET /api/courses/{courseId}`: Get a course by its ID.
*   `PUT /api/courses/{courseId}`: Update an existing course.
*   `DELETE /api/courses/{courseId}`: Delete a course.

### Enrollments

*   `POST /api/enrollments`: Enroll a student in a course.
*   `GET /api/enrollments`: Get a list of all enrollments.
*   `GET /api/enrollments/{enrollmentId}`: Get an enrollment by its ID.
*   `DELETE /api/enrollments/{enrollmentId}`: Delete an enrollment.

### Users

*   `PUT /api/users/{username}/password`: Change a user's password.

## Technologies Used

*   Spring Boot
*   Spring Data JPA
*   Spring Security
*   H2 Database
*   Lombok
*   Swagger
