package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.Telephone;

public interface TelephoneService {

	Telephone insert(Telephone telephone);
	Telephone update(Telephone telephone);
	void delete(Integer id);
	Telephone findById(Integer id);
	List<Telephone> listAll();
	List<Telephone> findByClient(Client client);
	List<Telephone> findByInstructor(Instructor instructor);
	List<Telephone> findByTelephone(String telephone);
}
