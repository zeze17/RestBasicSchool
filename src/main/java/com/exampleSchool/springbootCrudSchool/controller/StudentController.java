package com.exampleSchool.springbootCrudSchool.controller;

import com.exampleSchool.springbootCrudSchool.exceptions.ResourceNotFoundException;
import com.exampleSchool.springbootCrudSchool.model.Student;
import com.exampleSchool.springbootCrudSchool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/crud")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    //get all employees
    @GetMapping("/students")
    public List<Student> getallStudent() {
        return  studentRepository.findAll();
    }
    //crete student
    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student){
    return studentRepository.save(student);
    }

    //get student by id
    @GetMapping("students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long studentId) throws ResourceNotFoundException{
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("student not found for this id: " + studentId));
        return  ResponseEntity.ok().body(student);
    }
    //update Student
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long studentId,@RequestBody Student studentDetails) throws ResourceNotFoundException{
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("student not found for this id: " + studentId));
       student.setFirstName(studentDetails.getFirstName());
       student.setLastName(studentDetails.getLastName());
       student.setEmail(studentDetails.getEmail());
       student.setAge(studentDetails.getAge());
       student.setCourse(studentDetails.getCourse());
       studentRepository.save(student);

        return  ResponseEntity.ok().body(student);}

    // delete student
    @DeleteMapping("/students/{id}")
    public  ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long studentId)throws ResourceNotFoundException{
        studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));
        studentRepository.deleteById(studentId);
        return ResponseEntity.ok().build();}
}
