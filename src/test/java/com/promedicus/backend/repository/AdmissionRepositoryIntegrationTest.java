package com.promedicus.backend.repository;

import com.promedicus.backend.model.entity.Admission;
import com.promedicus.backend.model.type.Category;
import com.promedicus.backend.model.type.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AdmissionRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdmissionRepository admissionRepository;

    @Test
    public void testRepositoryFindAll() {
        //given
        Admission admission1 = new Admission();
        admission1.setPatientName("James");
        admission1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        admission1.setGender(Gender.MALE);
        admission1.setCategory(Category.EMERGENCY);

        Admission admission2 = new Admission();
        admission2.setPatientName("Lee");
        admission2.setDateOfBirth(LocalDate.of(1980, 12, 12));
        admission2.setGender(Gender.FEMALE);
        admission2.setCategory(Category.NORMAL);

        entityManager.persist(admission1);
        entityManager.persist(admission2);
        entityManager.flush();

        // when
        List<Admission> admissions = admissionRepository.findAll();

        //then
        assertThat(admissions.size()).isEqualTo(2);
    }

    @Test
    public void testRepositoryGetById() {
        //given
        Admission admission1 = new Admission();
        admission1.setPatientName("Tom");
        admission1.setDateOfBirth(LocalDate.of(1981, 01, 01));
        admission1.setGender(Gender.INTERSEX);
        admission1.setCategory(Category.EMERGENCY);

        admission1 = entityManager.persist(admission1);
        entityManager.flush();

        // when
        Admission admission = admissionRepository.getOne(admission1.getId());

        //then
        assertThat(admission.getId()).isEqualTo(admission1.getId());
        assertThat(admission.getPatientName()).isEqualToIgnoringCase(admission1.getPatientName());
        assertThat(admission.getDateOfBirth()).isEqualTo(admission1.getDateOfBirth());
        assertThat(admission.getGender()).isEqualTo(admission1.getGender());
        assertThat(admission.getCategory()).isEqualTo(admission1.getCategory());
    }

    @Test
    public void testRepositoryUpdate() {
        //given
        Admission admission1 = new Admission();
        admission1.setPatientName("Hanks");
        admission1.setDateOfBirth(LocalDate.of(1988, 8, 8));
        admission1.setGender(Gender.MALE);
        admission1.setCategory(Category.OUTPATIENT);

        admission1 = entityManager.persist(admission1);
        entityManager.flush();

        String patientName = "Tom Hanks";
        admission1.setPatientName(patientName);
        admission1.setGender(Gender.INTERSEX);

        // when
        Admission admission = admissionRepository.save(admission1);

        //then
        assertThat(admission.getPatientName()).isEqualToIgnoringCase(patientName);
        assertThat(admission.getGender()).isEqualTo(Gender.INTERSEX);
    }


    @Test (expected = JpaObjectRetrievalFailureException.class)
    public void testRepositoryDelete() {
        //given
        Admission admission1 = new Admission();
        admission1.setPatientName("Jessie");
        admission1.setDateOfBirth(LocalDate.of(1980, 8, 8));
        admission1.setGender(Gender.FEMALE);
        admission1.setCategory(Category.OUTPATIENT);

        admission1 = entityManager.persist(admission1);
        entityManager.flush();

        // before delete
        assertThat(admissionRepository.getOne(admission1.getId())).isNotNull();

        // when
        admissionRepository.deleteById(admission1.getId());

        //after delete throws entity not found exception
        admissionRepository.getOne(admission1.getId());
    }

}

