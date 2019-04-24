package com.promedicus.backend.repository;

import com.promedicus.backend.model.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tolim on 9/08/2018.
 */
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
