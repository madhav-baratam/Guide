package com.example.Guide.Guide.controller;

import com.example.Guide.Guide.model.Guide;
import com.example.Guide.Guide.service.GuideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GuideControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuideService guideService;
    @Autowired
    private ObjectMapper objectMapper;

    private Guide place1;
    private Guide place2;


    @BeforeEach
    void init()
    {
    place1 = new Guide();
    place1.setId(1L);
    place1.setCityName("Vizag");
    place1.setFamousPlace("central park");
    place1.setFromDate(LocalDate.of(2018, Month.AUGUST,7));

    place2 = new Guide();
    place2.setId(2L);
    place2.setCityName("Hyd");
    place2.setFamousPlace("IT hub");
    place2.setFromDate(LocalDate.of(2022, Month.AUGUST,7));

    }

    @Test
    void shouldCreateNewGuide() throws Exception
    {
        when(guideService.save(any(Guide.class))).thenReturn(place1);
        this.mockMvc.perform(post("/guide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(place1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cityName", CoreMatchers.is(place1.getCityName())))
                .andExpect(jsonPath("$.famousPlace", CoreMatchers.is(place1.getFamousPlace())))
                .andExpect(jsonPath("$.fromDate", CoreMatchers.is(place1.getFromDate().toString())));

    }
    @Test
    void shouldFetchAllMovies() throws Exception {

        List<Guide> list = new ArrayList<>();
        list.add(place1);
        list.add(place2);

        when(guideService.getAllGuides()).thenReturn(list);

        this.mockMvc.perform(get("/guide"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void shouldFetchOneGuideById() throws Exception
    {
        when(guideService.getGuideBYId(anyLong())).thenReturn(place1);
        this.mockMvc.perform(get("/guide/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is(place1.getCityName())))
                .andExpect(jsonPath("$.famousPlace", is(place1.getFamousPlace())));

    }

    @Test
    void shouldDeleteMovie() throws Exception {

        doNothing().when(guideService).deleteGuide(anyLong());

        this.mockMvc.perform(delete("/guide/{id}", 1L))
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldUpdateMovie() throws Exception {

        when(guideService.updateGuide(any(Guide.class), anyLong())).thenReturn(place1);
        this.mockMvc.perform(put("/guide/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(place1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is(place1.getCityName())))
                .andExpect(jsonPath("$.famousPlace", is(place1.getFamousPlace())));
    }

}
