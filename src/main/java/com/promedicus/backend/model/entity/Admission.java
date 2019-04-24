package com.promedicus.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.promedicus.backend.model.type.Category;
import com.promedicus.backend.model.type.Gender;
import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "admission")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID", scope = Admission.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Admission implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "admission_date")
	private LocalDateTime dateAdmission;

	@Column(name = "patient_name")
	@NotBlank(message = "Patient name is mandatory.")
	@Size(max = 40, message = "Invalid patient name. Max 40 characters long.")
	private String patientName;

	@Column(name = "date_of_birth")
	@NotNull(message = "Birth date is mandatory.")
	@PastOrPresent(message = "Birth date must not be in future.")
	private LocalDate dateOfBirth;

	@Enumerated(STRING)
	@Column(name = "gender")
	@NotNull(message = "Invalid gender value.")
	private Gender gender;

	@Enumerated(STRING)
	@Column(name = "category")
	@NotNull(message = "Invalid category value.")
	private Category category;

	@Column(name = "source")
	@Size(max = 100, message = "Invalid external source system name. Max 100 characters long.")
	private String source;

	@Version
	@JsonIgnore
	private Integer version;

	@PrePersist
	public void onAdmission() {
		dateAdmission = now();
	}
}
