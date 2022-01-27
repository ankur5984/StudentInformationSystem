package com.app.core.StudentInformationSystem.service;

import com.app.core.StudentInformationSystem.daos.IStudentRepository;
import com.app.core.StudentInformationSystem.exceptionHandler.StudentNotFoundException;
import com.app.core.StudentInformationSystem.model.Student;
import com.app.core.StudentInformationSystem.utils.AppConstant;
import com.app.core.StudentInformationSystem.utils.AppUtil;
import com.app.core.StudentInformationSystem.utils.SmtpConfiguration;
import org.hibernate.Session;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

    private final SmtpConfiguration smtp;

    public StudentService(IStudentRepository _repo, SmtpConfiguration _smtp){
        this.repo = _repo;
        this.smtp = _smtp;
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

        return mgr.unwrap(Session.class).createQuery(query,Student.class)
                .setParameter("username", username)
                .setParameter("password",password).getSingleResult();

    }

    public Student getStudentDetails(String username){

        String query = "select s from Student s where s.userName= :username";

        return mgr.unwrap(Session.class).createQuery(query,Student.class)
                .setParameter("username", username).getSingleResult();

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

    //forget password -> get full student
    public Student getStudentByEmail(String _email) throws StudentNotFoundException {
        Student student;
        student = repo.findStudentByEmail(_email);
        return student;
    }
    //send email
    public String sendEmail(String _email){
        try{
            Student student = this.getStudentByEmail(_email);
            if(student!=null){
             //  String pass = student.getPassword();
                String newPassword = AppUtil.getAlphaNumericString();
                student.setPassword(newPassword);
                student.setConfirmPassword(newPassword);

                Student _updatedStudent = this.updateStudentDetails(student,student.getId());

                //create mail sender
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost(this.smtp.getHost());
                mailSender.setPort(this.smtp.getPort());
                mailSender.setUsername(this.smtp.getUsername());
                mailSender.setPassword(this.smtp.getPassword());

                //Simple message instance consist -> from ,to,bcc,body
                SimpleMailMessage mailSchema = new SimpleMailMessage();
                mailSchema.setFrom(AppConstant.COMPANY_EMAIL_ADMIN);
                mailSchema.setTo(_updatedStudent.getEmail());
                mailSchema.setSubject(AppConstant.FORGET_PASSWORD_SUBJECT_LINE);
                String body = "Hi, "+_updatedStudent.getName() +", Your Password was reset successfully. Please use the the beloe password to login to your SMS Account. \n"+
                        " Password : "+ _updatedStudent.getPassword()+" \n Please donot share it with Anyone";
                mailSchema.setText(body);

                //schema is ready to stream
                //send email
                mailSender.send(mailSchema);
                return body;
            }


        }catch (StudentNotFoundException _ex){
            throw new StudentNotFoundException("Student not found");
        }



        return "wrong email provided";
    }


}
