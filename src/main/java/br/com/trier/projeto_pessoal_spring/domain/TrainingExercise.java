package br.com.trier.projeto_pessoal_spring.domain;

import br.com.trier.projeto_pessoal_spring.domain.dto.TrainingExerciseDTO;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "exercicio_treino")
public class TrainingExercise {
	
	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ficha_treino")
	private TrainingPlan trainingPlan;
	
	@ManyToOne
	@JoinColumn(name = "exercicio")
	private Exercise exercise;
	
	@Column(name = "serie")
	private Integer set;
	
	@Column(name = "repeticao")
	private Integer repetition;
	
	public TrainingExercise(TrainingExerciseDTO dto) {
		this(dto.getId(),
			 new TrainingPlan(dto.getTrainingPlan()),
			 new Exercise(dto.getExerciseId(), dto.getExerciseDescription()),
			 dto.getSet(),
			 dto.getRepetition());
	}
	
	public TrainingExerciseDTO toDTO() {
		return new TrainingExerciseDTO(id, 
									   trainingPlan.toDTO(), 
									   exercise.getId(), exercise.getDescription(),
									   set, repetition);
	}
	
}
