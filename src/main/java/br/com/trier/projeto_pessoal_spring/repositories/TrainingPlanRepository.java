package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer>{

	List<TrainingPlan> findByClient(Client client);
	List<TrainingPlan> findByInstructor(Instructor instructor);
	List<TrainingPlan> findByNameContainsIgnoreCase(String name);
	Optional<TrainingPlan> findByNameIgnoreCaseAndClient(String name, Client client);
}
