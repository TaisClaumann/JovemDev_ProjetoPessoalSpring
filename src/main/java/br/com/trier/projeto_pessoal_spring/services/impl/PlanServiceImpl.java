package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Plan;
import br.com.trier.projeto_pessoal_spring.repositories.PlanRepository;
import br.com.trier.projeto_pessoal_spring.services.PlanService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;

@Service
public class PlanServiceImpl implements PlanService{
	
	@Autowired
	private PlanRepository repository;

	@Override
	public Plan insert(Plan plan) {
		validatePlan(plan);
		return repository.save(plan);
	}
	
	private void validatePlan(Plan plan) {
		if(plan == null) {
			throw new IntegrityViolationException("O plano não pode ser nulo");
		} else if(plan.getDescription() == null || plan.getDescription().isBlank()) {
			throw new IntegrityViolationException("Preencha a descrição do plano");
		} else if(plan.getPrice() == null || plan.getPrice() == 0) {
			throw new IntegrityViolationException("O preço não deve ser nulo ou igual a 0");
		}
		validaDescription(plan);
	}
	
	private void validaDescription(Plan plan) {
		Optional<Plan> planFound = repository.findByDescriptionIgnoreCase(plan.getDescription());
		if(planFound.isPresent()) {
			if(!planFound.get().getId().equals(plan.getId())) {
				throw new IntegrityViolationException("A descrição desse plano já existe");
			}
		}
	}

	@Override
	public Plan update(Plan plan) {
		if(!listAll().contains(plan)) {
			throw new ObjectNotFoundException("Plano inexistente");
		}
		return insert(plan);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public Plan findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Plano %s inexistente".formatted(id)));
	}

	@Override
	public Plan findByPrice(Double price) {
		return repository.findByPrice(price).orElseThrow(() 
				-> new ObjectNotFoundException("Plano de R$%.2f inexistente".formatted(price)));
	}

	@Override
	public List<Plan> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há planos cadastrados");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<Plan> findByDescription(String description) {
		if(repository.findByDescriptionContainsIgnoreCase(description).isEmpty()) {
			throw new ObjectNotFoundException("Plano %s inexistente".formatted(description));
		}
		return repository.findByDescriptionContainsIgnoreCase(description);
	}

	@Override
	public List<Plan> findByPriceBetween(Double initialPrice, Double finalPrice) {
		if(repository.findByPriceBetweenOrderByPrice(initialPrice, finalPrice).isEmpty()) {
			throw new ObjectNotFoundException(
					"Não há planos de R$%.2f a R$%.2f".formatted(initialPrice, finalPrice));
		}
		return repository.findByPriceBetweenOrderByPrice(initialPrice, finalPrice);
	}
	
}
