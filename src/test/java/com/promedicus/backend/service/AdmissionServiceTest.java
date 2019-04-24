package com.promedicus.backend.service;

import com.promedicus.backend.model.entity.Admission;
import com.promedicus.backend.model.type.Category;
import com.promedicus.backend.model.type.Gender;
import com.promedicus.backend.repository.AdmissionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdmissionServiceTest {

    @Autowired
    private AdmissionService admissionService;

    @MockBean
    private AdmissionRepository admissionRepository;

    @Test
    public void testServiceListAllAdmissions() {
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

        List<Admission> expected = new ArrayList<>();
        expected.add(admission1);
        expected.add(admission2);

        when(admissionRepository.findAll()).thenReturn(expected);

        // when
        List<Admission> actual = admissionService.list();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
    }


    @Test
    public void testServiceGetAdmission() {
        //given
        Long id = 1L;

        Admission expected = new Admission();
        expected.setId(id);
        expected.setPatientName("James");
        expected.setDateOfBirth(LocalDate.of(1979, 01, 01));
        expected.setGender(Gender.MALE);
        expected.setCategory(Category.EMERGENCY);

        when(admissionRepository.getOne(id)).thenReturn(expected);

        // when
        Admission actual = admissionService.get(id);

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getPatientName()).isEqualToIgnoringCase(expected.getPatientName());
        assertThat(actual.getDateOfBirth()).isEqualTo(expected.getDateOfBirth());
        assertThat(actual.getGender()).isEqualTo(expected.getGender());
        assertThat(actual.getCategory()).isEqualTo(expected.getCategory());
    }

}
