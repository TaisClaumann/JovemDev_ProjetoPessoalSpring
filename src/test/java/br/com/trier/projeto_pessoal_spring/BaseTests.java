package br.com.trier.projeto_pessoal_spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.ExerciseService;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;
import br.com.trier.projeto_pessoal_spring.services.PlanService;
import br.com.trier.projeto_pessoal_spring.services.TelephoneService;
import br.com.trier.projeto_pessoal_spring.services.impl.ClientServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.ExerciseServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.InstructorServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.PlanServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.TelephoneServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {
	
	@Bean
	public PlanService planService() {
		return new PlanServiceImpl();
	}
	
	@Bean
	public ClientService clientService() {
		return new ClientServiceImpl();
	}
	
	@Bean
	public InstructorService instructorService() {
		return new InstructorServiceImpl();
	}
	
	@Bean
	public TelephoneService telephoneService() {
		return new TelephoneServiceImpl();
	}
	
	@Bean
	public ExerciseService exerciseService() {
		return new ExerciseServiceImpl();
	}
}
