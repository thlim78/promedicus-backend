package com.promedicus.backend.service;

import com.promedicus.backend.exception.EntityNotFoundException;
import com.promedicus.backend.model.entity.Admission;
import com.promedicus.backend.repository.AdmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tolim on 10/08/2018.
 */
public interface AdmissionService {
    Boolean exists(Long id);
    List<Admission> list();
    Admission get(Long id);
    Admission create(Admission admission);
    void update(Long id, Admission source);
    void delete(Long id);

    @Service
    @Transactional
    class Default implements AdmissionService {

        private final AdmissionRepository admissionRepository;

        public Default(AdmissionRepository admissionRepository) {
            this.admissionRepository = admissionRepository;
        }

        public Boolean exists(Long id) {
            return admissionRepository.existsById(id);
        }

        public List<Admission> list() {
            return admissionRepository.findAll();
        }

        public Admission get(Long id) {
            return admissionRepository.getOne(id);
        }

        public Admission create(Admission admission) {
            return admissionRepository.save(admission);
        }

        public void update (Long id, Admission source) {
            Admission target = admissionRepository.getOne(id);
            target.setPatientName(source.getPatientName());
            target.setDateOfBirth(source.getDateOfBirth());
            target.setGender(source.getGender());
            target.setCategory(source.getCategory());
            target.setSource(source.getSource());
            admissionRepository.save(target);
        }

        public void delete (Long id) {
            admissionRepository.deleteById(id);
        }
    }
}
