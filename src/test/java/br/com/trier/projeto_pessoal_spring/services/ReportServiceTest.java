package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import jakarta.transaction.Transactional;

@Transactional
public class ReportServiceTest extends BaseTests{
	
	@Autowired
	private ReportService service;
	@Autowired
	private TrainingExerciseService trainingExerciseService;
	
	@Test
	@DisplayName("Teste buscar os exercícios preferidos das fichas")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findExerciseTrendsTest() {
		var trainingExercises = trainingExerciseService.listAll();
		var exercices = service.findExerciseTrends(trainingExercises);
		assertEquals(3, exercices.size());
		assertEquals(4, exercices.get(0).getTrend());
		assertEquals(2, exercices.get(0).getExerciseId());
		assertEquals(3, exercices.get(1).getExerciseId());
		assertEquals(4, exercices.get(2).getExerciseId());
	}

}
