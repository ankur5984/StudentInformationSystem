package com.app.core.StudentInformationSystem.service;

import com.app.core.StudentInformationSystem.daos.IStudentRepository;
import com.app.core.StudentInformationSystem.exceptionHandler.StudentNotFoundException;
import com.app.core.StudentInformationSystem.model.Student;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentService{

    @PersistenceContext
    private EntityManager mgr; //issue occurred @Autowiring is not working. so used injected in constructor
    private final IStudentRepository repo;

    public StudentService(IStudentRepository _repo){
        this.repo = _repo;
    }


    //crud : CREATE,RETRIEVE,UPDATE,DELETE

    //insert
    public Student addStudent(Student _student){
        if(_student == null){
            return null;
        }
        return repo.save(_student);
    }

    //Retrieve
    //1. getAllStudents
    public List<Student> findAllStudent(){
        return repo.findAll();
    }

    //2. getByID
    public Student getStudentById(Long _id){
      //  return repo.findStudentById(_id).orElse(new Student());
        return repo.findStudentById(_id)
                .orElseThrow(() -> new StudentNotFoundException("Student by id " + _id + " not found on DataBase"));
    }

    public Student getStudentDetails(String username, String password){

        String query = "select s from Student s where s.userName= :username and s.password= :password";

        if(query==null){
            return mgr.unwrap(Session.class).createQuery(query,Student.class)
                    .setParameter("username", username)
                    .setParameter("password",password).getSingleResult();
        }
        return null;

    }

    public Student getStudentDetails(String username){

        String query = "select s from Student s where s.userName= :username";

        if(query!=null){
            return mgr.unwrap(Session.class).createQuery(query,Student.class)
                    .setParameter("username", username).getSingleResult();
        }
        return null;

    }

    //update
    public Student updateStudentDetails(Student _student, Long _id){
        if(_student == null){
            return null;
        }
        Student existingStudent = this.getStudentById(_id);

        if(existingStudent != null){
            existingStudent.setId(_student.getId());
            existingStudent.setName(_student.getName());
            existingStudent.setUserName(_student.getUserName());
            existingStudent.setAddress(_student.getAddress());
            existingStudent.setEmail(_student.getEmail());
            existingStudent.setPassword(_student.getPassword());
            existingStudent.setConfirmPassword(_student.getConfirmPassword());
            existingStudent.setPhone(_student.getPhone());
            existingStudent.setRole(_student.getRole());
        }
        mgr.unwrap(Session.class).update(existingStudent);
        return existingStudent;
    }

    //delete
    public void deleteStudent(Long _id){
     repo.deleteStudentById(_id);
    }

}
