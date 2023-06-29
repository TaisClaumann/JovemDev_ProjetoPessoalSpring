package br.com.trier.projeto_pessoal_spring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.ProjetoPessoalSpringApplication;
import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.spring_matutino.config.jwt.LoginDTO;
import br.com.trier.spring_matutino.domain.Pais;
import jakarta.servlet.http.HttpServletRequest;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjetoPessoalSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExerciseResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<Exercise> getExercise(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 Exercise.class);
				
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Exercise>> getExercises(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 new ParameterizedTypeReference<List<Exercise>>() {
		});
	}
	
	@Test
	@DisplayName("Teste inserir exercício com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	void insertAdminTest() {
		Exercise exercise = new Exercise(null, "Flexão");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises", HttpMethod.POST, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("Flexão", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste inserir exercício com descrição duplicada")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void insertDuplicatedTest() {
		Exercise exercise = new Exercise(null, "Flexão");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises", HttpMethod.POST, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste inserir exercício inválido")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void insertInvalidTest() {
		Exercise exercise = new Exercise(null, "");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises", HttpMethod.POST, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste inserir exercício com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	void insertUserTest() {
		Exercise exercise = new Exercise(null, "Flexão");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises", HttpMethod.POST, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste alterar exercício com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void updateAdminTest() {
		Exercise exercise = new Exercise(2, "teste");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises/2", HttpMethod.PUT, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste alterar exercício com descrição duplicada")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void updateDuplicatedTest() {
		Exercise exercise = new Exercise(2, "Cadeira Adutora");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises/2", HttpMethod.PUT, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste alterar exercício com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void updateUserTest() {
		Exercise exercise = new Exercise(2, "teste");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Exercise> requestEntity = new HttpEntity<>(exercise, headers);
		ResponseEntity<Exercise> responseEntity = rest.exchange("/exercises/2", HttpMethod.PUT, requestEntity, Exercise.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste deletar exercício com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void deleteAdminTest() {
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/exercises/2", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste deletar exercício inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void deleteNotFoundTest() {
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/exercises/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste deletar exercício com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void deleteUserTest() {
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/exercises/2", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste listar todos os exercícios com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void listAllAdminTest() {
		ResponseEntity<List<Exercise>> response = rest.exchange(
				"/exercises", 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("test@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Exercise>>() {} 
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(6, response.getBody().size());
	}
	
	@Test
	@DisplayName("Teste listar todos os exercícios com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void listAllUserTest() {
		ResponseEntity<List<Exercise>> response = rest.exchange(
				"/exercises", 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("test2@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Exercise>>() {} 
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(6, response.getBody().size());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios pelo id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByIdTest() {
		ResponseEntity<Exercise> response = getExercise("/exercises/2");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		Exercise exercise = response.getBody();
		assertEquals("Cadeira Abdutora", exercise.getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByIdNotFoundTest() {
		ResponseEntity<Exercise> response = getExercise("/exercises/1");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste buscar exercício pela descrição ignorando o case")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByNameEqualsIgnoreCaseTest() {
		ResponseEntity<List<Exercise>> response = getExercises("/exercises/description/Cadeira");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
}
