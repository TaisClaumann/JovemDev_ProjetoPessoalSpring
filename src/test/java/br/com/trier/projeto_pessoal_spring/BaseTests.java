package br.com.trier.projeto_pessoal_spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.dto.ReportExerciseTrendsDTO;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.ExerciseService;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;
import br.com.trier.projeto_pessoal_spring.services.PlanService;
import br.com.trier.projeto_pessoal_spring.services.ReportService;
import br.com.trier.projeto_pessoal_spring.services.TelephoneService;
import br.com.trier.projeto_pessoal_spring.services.TrainingExerciseService;
import br.com.trier.projeto_pessoal_spring.services.TrainingPlanService;
import br.com.trier.projeto_pessoal_spring.services.UserService;
import br.com.trier.projeto_pessoal_spring.services.impl.ClientServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.ExerciseServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.InstructorServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.PlanServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.ReportServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.TelephoneServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.TrainingExerciseServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.TrainingPlanServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.UserServiceImpl;

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
	
	@Bean
	public TrainingPlanService trainingPlanService() {
		return new TrainingPlanServiceImpl();
	}
	
	@Bean
	public TrainingExerciseService trainingExerciseService() {
		return new TrainingExerciseServiceImpl();
	}
	
	@Bean
	public ReportService reportService() {
		return new ReportServiceImpl();
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
}
