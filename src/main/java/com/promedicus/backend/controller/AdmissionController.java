package com.promedicus.backend.controller;

import com.promedicus.backend.exception.EntityNotFoundException;
import com.promedicus.backend.model.entity.Admission;
import com.promedicus.backend.service.AdmissionService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/admissions")
public class AdmissionController {

	private final AdmissionService admissionService;

	public AdmissionController (AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	@GetMapping("/")
	public List<Admission> list() {
		return admissionService.list();
	}

	@GetMapping("/{id}")
	public Admission get(@PathVariable Long id) {
		return admissionService.get(id);
	}

	@PostMapping("/")
	public Admission create(@Valid @RequestBody Admission admission) {
		return admissionService.create(admission);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @Valid @RequestBody Admission source) {
		validateRequest(id);
		admissionService.update(id, source);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		validateRequest(id);
		admissionService.delete(id);
	}

	private void validateRequest(Long id) {
		if (!admissionService.exists(id)) {
			throw new EntityNotFoundException("admission", id);
		}
	}
}
