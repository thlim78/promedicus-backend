package com.promedicus.backend.controller;

import com.promedicus.backend.model.entity.Admission;
import com.promedicus.backend.model.type.Category;
import com.promedicus.backend.model.type.Gender;
import com.promedicus.backend.service.AdmissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdmissionController.class)
public class AdmissionControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdmissionService service;

    @Test
    public void testListAllAdmissions() throws Exception {

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

        List<Admission> allAdmissions = Arrays.asList(admission1, admission2);

        given(service.list()).willReturn(allAdmissions);

        mvc.perform(get("/admissions/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].patientName", is(admission1.getPatientName())))
                .andExpect(jsonPath("$[0].gender", is(admission1.getGender().getDescription())))
                .andExpect(jsonPath("$[0].category", is(admission1.getCategory().getDescription())));
    }


    @Test
    public void testGetAdmission() throws Exception {

        Long id = 1L;

        Admission admission1 = new Admission();
        admission1.setId(id);
        admission1.setPatientName("James");
        admission1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        admission1.setGender(Gender.MALE);
        admission1.setCategory(Category.EMERGENCY);

        given(service.get(id)).willReturn(admission1);

        mvc.perform(get("/admissions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientName", is(admission1.getPatientName())))
                .andExpect(jsonPath("$.gender", is(admission1.getGender().getDescription())))
                .andExpect(jsonPath("$.category", is(admission1.getCategory().getDescription())));
    }

    @Test
    public void testCreateAValidAdmission() throws Exception {

        Long id = 1L;

        Admission admission1 = new Admission();
        admission1.setId(id);
        admission1.setPatientName("James");
        admission1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        admission1.setGender(Gender.MALE);
        admission1.setCategory(Category.EMERGENCY);
        admission1.setSource("Hospital A");

        given(service.create(admission1)).willReturn(admission1);

        String json = "{\"@UUID\":\"67792bbb-0446-4a75-bfce-db3bbf342b96\",\"id\":1,\"dateAdmission\":null,\"patientName\":\"James\",\"dateOfBirth\":\"1979-01-01\",\"gender\":\"Male\",\"category\":\"Emergency\",\"source\":\"Hospital A\"}";

        mvc.perform(post("/admissions/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientName", is(admission1.getPatientName())))
                .andExpect(jsonPath("$.gender", is(admission1.getGender().getDescription())))
                .andExpect(jsonPath("$.category", is(admission1.getCategory().getDescription())))
                .andExpect(jsonPath("$.source", is(admission1.getSource())));
    }

    @Test
    public void testCreateAInvalidAdmissionReturnsBadRequestStatus() throws Exception {

        Long id = 1L;

        Admission admission1 = new Admission();
        admission1.setId(id);
        admission1.setPatientName("James");
        admission1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        admission1.setGender(Gender.MALE);
        admission1.setCategory(Category.EMERGENCY);

        given(service.create(admission1)).willReturn(admission1);

        String jsonInvalidCategory = "{\"@UUID\":\"67792bbb-0446-4a75-bfce-db3bbf342b96\",\"id\":1,\"dateAdmission\":null,\"patientName\":\"James\",\"dateOfBirth\":\"1979-01-01\",\"gender\":\"Male\",\"category\":\"Invalid Category\",\"source\":null}";

        mvc.perform(post("/admissions/")
                .content(jsonInvalidCategory)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testUpdateAValidAdmission() throws Exception {

        Long id = 1L;

        Admission admission1 = new Admission();
        admission1.setId(id);
        admission1.setPatientName("Jessie");
        admission1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        admission1.setGender(Gender.FEMALE);
        admission1.setCategory(Category.NORMAL);

        given(service.exists(id)).willReturn(true);
        doNothing().when(service).update(id, admission1);

        String jsonUpdated = "{\"@UUID\":\"67792bbb-0446-4a75-bfce-db3bbf342b96\",\"id\":1,\"dateAdmission\":null,\"patientName\":\"Jessie\",\"dateOfBirth\":\"1979-01-01\",\"gender\":\"Female\",\"category\":\"Normal\",\"source\":null}";

        mvc.perform(put("/admissions/" + id)
                .content(jsonUpdated)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testControllerDeleteAValidAdmission() throws Exception {

        Long id = 1L;

        given(service.exists(id)).willReturn(true);
        doNothing().when(service).delete(id);

        mvc.perform(delete("/admissions/"+ id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
