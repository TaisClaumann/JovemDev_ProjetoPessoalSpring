package br.com.trier.projeto_pessoal_spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.PlanService;
import br.com.trier.projeto_pessoal_spring.services.impl.ClientServiceImpl;
import br.com.trier.projeto_pessoal_spring.services.impl.PlanServiceImpl;

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

}
