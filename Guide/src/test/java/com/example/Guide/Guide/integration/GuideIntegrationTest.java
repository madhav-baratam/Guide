package com.example.Guide.Guide.integration;

import com.example.Guide.Guide.repository.GuideRepository;
import com.example.Guide.Guide.model.Guide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuideIntegrationTest {
    @LocalServerPort
    private int port;
    private String baseUrl ="http://localhost";
    private static RestTemplate restTemplate;
    private Guide place1;

    private Guide place2;

    @Autowired
    private GuideRepository guideRepository;

    @BeforeAll
    public static void init()
    {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/guide";

        place1 = new Guide();
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST,7));

        place2 = new Guide();
        place2.setCityName("Hyd");
        place2.setFamousPlace("IT hub");
        place2.setFromDate(LocalDate.of(2022, Month.AUGUST,7));

        place1 = guideRepository.save(place1);
        place2 = guideRepository.save(place2);
    }
    @AfterEach
    public void afterSetup() {
        guideRepository.deleteAll();
    }
    @Test
    void shouldCreateGuideTest() {
        Guide place3 = new Guide();
        place3.setCityName("guntur");
        place3.setFamousPlace("It hub");
        place3.setFromDate(LocalDate.of(2021, Month.DECEMBER, 31));

        Guide newGuide = restTemplate.postForObject(baseUrl, place3, Guide.class);

        assertNotNull(newGuide);
        assertThat(newGuide.getId()).isNotNull();
    }
    @Test
    void shouldFetchGuideTest() {

        List<Guide> list = restTemplate.getForObject(baseUrl, List.class);

        assertThat(list.size()).isEqualTo(2);
    }
    @Test
    void shouldFetchOneGuideByIdTest() {

        Guide existingGuide = restTemplate.getForObject(baseUrl+"/"+place1.getId(), Guide.class);

        assertNotNull(existingGuide);
        assertEquals("Vizag", existingGuide.getCityName());
    }
    @Test
    void shouldDeleteGuideTest() {

        restTemplate.delete(baseUrl+"/"+place1.getId());

        int count = guideRepository.findAll().size();

        assertEquals(1, count);
    }

    @Test
    void shouldUpdateMGuideTest() {

        place1.setFamousPlace("Zoo");

        restTemplate.put(baseUrl+"/{id}", place1, place1.getId());

        Guide existingGuide = restTemplate.getForObject(baseUrl+"/"+place1.getId(), Guide.class);

        assertNotNull(existingGuide);
        assertEquals("Zoo", existingGuide.getFamousPlace());
    }
}
