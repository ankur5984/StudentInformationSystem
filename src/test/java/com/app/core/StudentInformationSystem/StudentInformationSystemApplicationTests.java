package com.app.core.StudentInformationSystem;

import com.app.core.StudentInformationSystem.daos.IStudentRepository;
import com.app.core.StudentInformationSystem.model.Role;
import com.app.core.StudentInformationSystem.model.Student;
import com.app.core.StudentInformationSystem.service.StudentService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentInformationSystemApplicationTests {

	@Autowired
	private StudentService service;

	//mocked the repo.
	@MockBean
	private IStudentRepository repository;

	Student student1 = new Student("Ankur","ankur","root123","root123","9041917232","","ankur@gmail.com","hazaribag", Role.ADMIN);
	Student student2 = new Student("Akash","ankur","root123","root123","9041917232","","ankur@gmail.com","hazaribag", Role.STUDENT);

//	@Test
//	void contextLoads() {
//	}

	@Test
	public void getStudentList(){
		//stubbing first
		when(repository.findAll()).thenReturn(
				Stream.of(
					student1,student2
				).collect(Collectors.toList())
		);
		//testing now
		Assert.assertEquals(2,service.findAllStudent().size());
	}

	@Test
	public void getStudentById(){
		student1.setId(Long.valueOf(1));
		when(repository.findStudentById(student1.getId())).thenReturn(
				Stream.of(student1).findFirst()
		);
		Assert.assertEquals(student1,service.getStudentById(Long.valueOf(1)));
	}

	@Test
	public void addStudent(){
		Student _student = new Student("Nishant","nishi","root123","root123","9041917232","","ankur@gmail.com","hazaribag", Role.STUDENT);
		_student.setId(3L);
		when(repository.save(_student)).thenReturn(_student);

		Assert.assertEquals(_student,service.addStudent(_student));
	}

	@Test
	public  void deleteStudentById(){
		student1.setId(Long.valueOf(1));
		student2.setId(Long.valueOf(2));
		//firstly call delete method of service class
		service.deleteStudent(student2.getId());

		//for delete we need to verify whether the repository method is called or not
		verify(repository,times(1)).deleteStudentById(2L);
	}
}
