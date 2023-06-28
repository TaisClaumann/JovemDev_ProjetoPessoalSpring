package br.com.trier.projeto_pessoal_spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.services.ExerciseService;

@RestController
@RequestMapping(value = "exercises")
public class ExerciseResource {

	@Autowired
	private ExerciseService service;
	
	@PostMapping
	public ResponseEntity<Exercise> insert(@RequestBody Exercise exercise){
		return ResponseEntity.ok(service.insert(exercise));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Exercise> update(@PathVariable Integer id, @RequestBody Exercise exercise){
		exercise.setId(id);
		return ResponseEntity.ok(service.update(exercise));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Exercise> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Exercise>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<Exercise>> findByDescription(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescription(description));
	}
}
