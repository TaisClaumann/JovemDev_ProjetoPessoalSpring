package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;

import br.com.trier.projeto_pessoal_spring.domain.Plan;

public interface PlanService {

	Plan insert(Plan plan);
	Plan update(Plan plan);
	void delete(Integer id);
	Plan findById(Integer id);
	Plan findByPrice(Double price);
	List<Plan> listAll();
	List<Plan> findByDescription(String description);
	List<Plan> findByPriceBetween(Double initialPrice, Double finalPrice);
}
