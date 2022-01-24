package com.app.core.StudentInformationSystem.controllers;

import com.app.core.StudentInformationSystem.model.Student;
import com.app.core.StudentInformationSystem.model.User;
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

    @PostMapping("/authentication")
    public ResponseEntity<?> getStudent(@RequestBody User _user) {
        Student _student = _service.getStudentDetails(_user.getUsername(), _user.getPassword());
        if (_student == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(_student, HttpStatus.OK);
    }

    @PostMapping("/getByName")
    public ResponseEntity<?> getStudentByUserName(@RequestBody User _user) {
        Student _student = _service.getStudentDetails(_user.getUsername());
        if (_student == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(_student, HttpStatus.OK);
    }

    @GetMapping("/fetchById/{id}")
    public ResponseEntity<?> fetchById(@PathVariable("id") Long id){
        Student student = _service.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerEntry(@RequestBody Student _student){
        if(_student.getPassword().equals(_student.getConfirmPassword())) {
            Student newStudent = _service.addStudent(_student);
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Password mismatch",HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDetails(@RequestBody Student _student, @PathVariable("id") Long _id) {

        if (_student.getPassword().equals(_student.getConfirmPassword())) {
            Student newStudent = _service.updateStudentDetails(_student,_id);
            return new ResponseEntity<>(newStudent, HttpStatus.OK);
        }
        return new ResponseEntity<>("Password mismatch", HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/removeById")
    public ResponseEntity<?> removeById(@RequestParam Long id){
        _service.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
