package br.com.trier.projeto_pessoal_spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projeto_pessoal_spring.domain.dto.ReportExerciseTrendsDTO;
import br.com.trier.projeto_pessoal_spring.services.ReportService;
import br.com.trier.projeto_pessoal_spring.services.TrainingExerciseService;

@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	private ReportService service;
	@Autowired
	private TrainingExerciseService trainingExerciseService;
	
	@GetMapping("/exercise-trends")
	public ResponseEntity<List<ReportExerciseTrendsDTO>> findExerciseTrends(){
		return ResponseEntity.ok(service.findExerciseTrends(trainingExerciseService.listAll()));
	}

}
