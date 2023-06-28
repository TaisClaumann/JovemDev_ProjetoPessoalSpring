package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{
	
	List<Exercise> findByDescriptionContainsIgnoreCase(String description);
	Optional<Exercise> findByDescriptionIgnoreCase(String description);
}
