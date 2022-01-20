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

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentInformationSystemApplicationTests {

	@Autowired
	private StudentService service;

	@MockBean
	private IStudentRepository repository;

	Student student1 = new Student("Ankur","ankur","root123","root123","9041917232","","ankur@gmail.com","hazaribag", Role.ADMIN);
	Student student2 = new Student("Akash","ankur","root123","root123","9041917232","","ankur@gmail.com","hazaribag", Role.STUDENT);

	@Test
	void contextLoads() {
	}

	@Test
	public void getStudentList(){
		when(repository.findAll()).thenReturn(
				Stream.of(
					student1,student2
				).collect(Collectors.toList())
		);
		Assert.assertEquals(2,service.findAllStudent().size());
	}

}
