package com.app.core.StudentInformationSystem.daos;

import com.app.core.StudentInformationSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IStudentRepository extends JpaRepository<Student, Long> {
    void deleteStudentById(Long id);

    Optional<Student> findStudentById(Long id);

    @Query("from Student s where s.email=?1")
    Student findStudentByEmail(String email);
}
