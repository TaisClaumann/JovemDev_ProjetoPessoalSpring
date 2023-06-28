package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;
import br.com.trier.projeto_pessoal_spring.repositories.TrainingPlanRepository;
import br.com.trier.projeto_pessoal_spring.services.TrainingPlanService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;

@Service
public class TrainingPlanServiceImpl implements TrainingPlanService{

	@Autowired
	private TrainingPlanRepository repository;
	
	@Override
	public TrainingPlan insert(TrainingPlan trainingPlan) {
		validateTrainingPlan(trainingPlan);
		return repository.save(trainingPlan);
	}
	
	private void validateTrainingPlan(TrainingPlan trainingPlan) {
		if(trainingPlan == null) {
			throw new IntegrityViolationException("A ficha de treino não pode ser nula");
		} else if(trainingPlan.getName() == null || trainingPlan.getName().isBlank()) {
			throw new IntegrityViolationException("Preencha o nome da ficha");
		}
		validateNameTrainingPlan(trainingPlan);
	}
	
	private void validateNameTrainingPlan(TrainingPlan trainingPlan) {
		Optional<TrainingPlan> trainingFound = repository.findByNameIgnoreCaseAndClient(trainingPlan.getName(), trainingPlan.getClient());
		if(trainingFound.isPresent()) {
			if(!trainingFound.get().getId().equals(trainingPlan.getId())) {
				throw new IntegrityViolationException("O cliente %s já possui a ficha %s"
						.formatted(trainingPlan.getClient().getId(), trainingPlan.getName()));
			}
		}
	}

	@Override
	public TrainingPlan update(TrainingPlan trainingPlan) {
		if(!listAll().contains(trainingPlan)) {
			throw new ObjectNotFoundException("Ficha inexistente");
		}
		return insert(trainingPlan);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public TrainingPlan findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Ficha %s inexistente".formatted(id)));
	}

	@Override
	public List<TrainingPlan> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há fichas de treino cadastradas");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<TrainingPlan> findByClient(Client client) {
		if(repository.findByClient(client).isEmpty()) {
			throw new ObjectNotFoundException("Não há fichas de trieno do client: " + client.getId());
		}
		return repository.findByClient(client);
	}

	@Override
	public List<TrainingPlan> findByInstructor(Instructor instructor) {
		if(repository.findByInstructor(instructor).isEmpty()) {
			throw new ObjectNotFoundException("Não há fichas de trieno do instrutor: " + instructor.getId());
		}
		return repository.findByInstructor(instructor);
	}

	@Override
	public List<TrainingPlan> findByName(String name) {
		if(repository.findByNameContainsIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFoundException("Ficha %s inexistente".formatted(name));
		}
		return repository.findByNameContainsIgnoreCase(name);
	}

	@Override
	public TrainingPlan findByNameAndClient(String name, Client client) {
		return repository.findByNameIgnoreCaseAndClient(name, client).orElseThrow(() 
				-> new ObjectNotFoundException("Cliente %s não possui ficha com nome: %s".formatted(client.getId(), name)));
	}
}
