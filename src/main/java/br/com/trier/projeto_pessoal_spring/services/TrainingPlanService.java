package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;

public interface TrainingPlanService {

	TrainingPlan insert(TrainingPlan trainingPlan);
	TrainingPlan update(TrainingPlan trainingPlan);
	void delete(Integer id);
	TrainingPlan findById(Integer id);
	List<TrainingPlan> listAll();
	List<TrainingPlan> findByClient(Client client);
	List<TrainingPlan> findByInstructor(Instructor instructor);
	List<TrainingPlan> findByName(String name);
	TrainingPlan findByNameAndClient(String name, Client client);
}
