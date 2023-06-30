package br.com.trier.projeto_pessoal_spring.domain;

import br.com.trier.projeto_pessoal_spring.domain.dto.TelephoneClientDTO;
import br.com.trier.projeto_pessoal_spring.domain.dto.TelephoneDTO;
import br.com.trier.projeto_pessoal_spring.domain.dto.TelephoneInstructorDTO;
import br.com.trier.projeto_pessoal_spring.utils.TelephoneUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "telefone")
public class Telephone {
	
	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "aluno")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "instrutor")
	private Instructor instructor;
	
	@Column(name = "telefone")
	private String telephone;
	
	public Telephone(Integer id, Client client, Instructor instructor, String telephone) {
		this.id = id;
		this.client = client;
		this.instructor = instructor;
		this.telephone = TelephoneUtil.formatTelephone(telephone);
	}
	
	public TelephoneDTO toDTO() {
		if(instructor == null) {
			return new TelephoneDTO(
					id, client.getId(), client.getName(), client.getCpf(), null, null, null, telephone);
		}
		return new TelephoneDTO(
				id, null, null, null, instructor.getId(), instructor.getName(), instructor.getCpf(), telephone);
	}
	
	public TelephoneClientDTO toClientDTO() {
		return new TelephoneClientDTO(
				id, client.getId(), client.getName(), client.getCpf(), telephone);
	}
	
	public TelephoneInstructorDTO toInstructorDTO() {
		return new TelephoneInstructorDTO(
				id, instructor.getId(), instructor.getName(), instructor.getCpf(), telephone);
	}
}
