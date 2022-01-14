package com.app.core.StudentInformationSystem.controllers;

import com.app.core.StudentInformationSystem.model.Student;
import com.app.core.StudentInformationSystem.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/do.api.student")
public class StudentResourceController {

    private final StudentService _service;

    public StudentResourceController(StudentService _service) {
        this._service = _service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> fetchAll(){
        List<Student> _students = _service.findAllStudent();

        if(_students!=null){
            return new ResponseEntity<>(_students, HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/studentDetails")
    public ResponseEntity<?> getStudent(@RequestParam("username") String username, @RequestParam("password")  String password) {
        Student _student = _service.getStudentDetails(username, password);
        if (_student == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(_student, HttpStatus.OK);
    }

    @GetMapping("/fetchById")
    public ResponseEntity<Student> fetchById(@RequestParam Long id){
        Student student = _service.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerEntry(@RequestBody Student _student){
        if(_student.getPassword().equals(_student.getConfirmPassword())) {
            Student newStudent = _service.addStudent(_student);
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Password mismatch",HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateDetails(@RequestBody Student _student){
        Student updateStudent = _service.updateStudentDetails(_student);
        return new ResponseEntity<>(updateStudent,HttpStatus.OK);
    }

    @DeleteMapping("/removeById")
    public ResponseEntity<?> removeById(@RequestParam Long id){
        _service.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
