package br.com.trier.projeto_pessoal_spring.domain;

import br.com.trier.projeto_pessoal_spring.domain.dto.TrainingPlanDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "ficha_treino")
public class TrainingPlan {
	
	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "aluno")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "instrutor")
	private Instructor instructor;

	public TrainingPlan(TrainingPlanDTO dto) {
		this(dto.getId(), 
			 dto.getName(),
			 new Client(dto.getClient()),
			 new Instructor(dto.getInstructorId(), dto.getInstructorName(), dto.getInstructorCpf(), null));
	}
	
	public TrainingPlanDTO toDTO() {
		return new TrainingPlanDTO(
				id, name, client.toDTO(), instructor.getId(), instructor.getName(), instructor.getCpf());
	}
}
