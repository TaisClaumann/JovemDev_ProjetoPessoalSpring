package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>{
	
	List<Plan> findByDescriptionContainsIgnoreCase(String description);
	Optional<Plan> findByPrice(Double price);
	Plan findByDescriptionIgnoreCase(String description);
	List<Plan> findByPriceBetweenOrderByPrice(Double initialPrice, Double finalPrice);
}
