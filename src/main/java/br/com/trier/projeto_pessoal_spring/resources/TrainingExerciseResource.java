package br.com.trier.projeto_pessoal_spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.dto.TrainingExerciseDTO;
import br.com.trier.projeto_pessoal_spring.services.ExerciseService;
import br.com.trier.projeto_pessoal_spring.services.TrainingExerciseService;
import br.com.trier.projeto_pessoal_spring.services.TrainingPlanService;

@RestController
@RequestMapping(value = "/training-exercises")
public class TrainingExerciseResource {

	@Autowired
	private TrainingExerciseService service;
	@Autowired
	private ExerciseService exerciseService;
	@Autowired 
	private TrainingPlanService trainingPlanService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<TrainingExerciseDTO> insert(@RequestBody TrainingExerciseDTO trainingExerciseDTO){
		return ResponseEntity.ok(service.insert(
				new TrainingExercise(trainingExerciseDTO.getId(),
						  	    	 trainingPlanService.findById(trainingExerciseDTO.getTrainingPlan().getId()),
						  	    	 exerciseService.findById(trainingExerciseDTO.getExerciseId()),
						  	    	 trainingExerciseDTO.getSet(),
						  	    	 trainingExerciseDTO.getRepetition())).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<TrainingExerciseDTO> update(@PathVariable Integer id, @RequestBody TrainingExerciseDTO trainingExerciseDTO){
		TrainingExercise trainingExercise = 
				new TrainingExercise(trainingExerciseDTO.getId(),
	  	    	 					 trainingPlanService.findById(trainingExerciseDTO.getTrainingPlan().getId()),
	  	    	 					 exerciseService.findById(trainingExerciseDTO.getExerciseId()),
	  	    	 					 trainingExerciseDTO.getSet(),
	  	    	 					 trainingExerciseDTO.getRepetition());
		trainingExercise.setId(id);
		return ResponseEntity.ok(service.update(trainingExercise).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<TrainingExerciseDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<TrainingExerciseDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map(TrainingExercise::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/trainingPlan/{trainingPlanId}")
	public ResponseEntity<List<TrainingExerciseDTO>> findByTrainingPlan(@PathVariable Integer trainingPlanId){
		return ResponseEntity.ok(service.findByTrainingPlan(
				trainingPlanService.findById(trainingPlanId)).stream().map(TrainingExercise::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/exercise/{exerciseId}")
	public ResponseEntity<List<TrainingExerciseDTO>> findByExercise(@PathVariable Integer exerciseId){
		return ResponseEntity.ok(service.findByExercise(
				exerciseService.findById(exerciseId)).stream().map(TrainingExercise::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/set/{set}")
	public ResponseEntity<List<TrainingExerciseDTO>> findBySet(@PathVariable Integer set){
		return ResponseEntity.ok(service.findBySet(set).stream().map(TrainingExercise::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/repetition/{repetition}")
	public ResponseEntity<List<TrainingExerciseDTO>> findByRepetition(@PathVariable Integer repetition){
		return ResponseEntity.ok(
				service.findByRepetition(repetition).stream().map(TrainingExercise::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/set-repetition/{set}/{repetition}")
	public ResponseEntity<List<TrainingExerciseDTO>> findBySetAndRepetition(@PathVariable Integer set, @PathVariable Integer repetition){
		return ResponseEntity.ok(
				service.findBySetAndRepetition(set, repetition).stream().map(TrainingExercise::toDTO).toList());
	}
}
