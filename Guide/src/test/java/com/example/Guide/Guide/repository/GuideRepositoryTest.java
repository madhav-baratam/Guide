package com.example.Guide.Guide.repository;

import com.example.Guide.Guide.model.Guide;
import com.example.Guide.Guide.service.GuideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GuideRepositoryTest {
    @Mock
    private GuideRepository guideRepository;
    @InjectMocks
    private GuideService guideService;
    private Guide place1;
    private Guide place2;


    @BeforeEach
    void init() {
        place1 = new Guide();
        place1.setId(1L);
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST, 7));

        place2 = new Guide();
        place2.setId(2L);
        place2.setCityName("Hyd");
        place2.setFamousPlace("IT hub");
        place2.setFromDate(LocalDate.of(2022, Month.AUGUST, 7));

    }
    @Test
    @DisplayName("It should save the guide to the database")
    void save() {
       Guide place1 = new Guide();
        place1.setId(1L);
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST, 7));
        when(guideRepository.save(any(Guide.class))).thenReturn(place1);
       Guide newGuide = guideRepository.save(place1);
        assertNotNull(newGuide);
        assertThat(newGuide.getId()).isNotEqualTo(null);
    }
    @Test
    @DisplayName("It should return the guides list with size of 2")
    void getAllGuides()
    {
        place1 = new Guide();
        place1.setId(1L);
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST, 7));

        place2 = new Guide();
        place2.setId(2L);
        place2.setCityName("Hyd");
        place2.setFamousPlace("IT hub");
        place2.setFromDate(LocalDate.of(2022, Month.AUGUST, 7));

        guideRepository.save(place1);
        guideRepository.save(place2);

        List<Guide> list= new ArrayList<>();
        list.add(place1);
        list.add(place2);

        when(guideRepository.findAll()).thenReturn(list);
        List<Guide> guides = guideRepository.findAll();
        assertNotNull(guides);
        assertThat(guides).isNotNull();
        assertEquals(2, guides.size());

    }

    @Test
    @DisplayName("It should return the Guides list with famousPlace IT hub")
    void getMoviesByFamousPlace()
    {
        place1 = new Guide();
        place1.setId(1L);
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST, 7));

        place2 = new Guide();
        place2.setId(2L);
        place2.setCityName("Hyd");
        place2.setFamousPlace("IT hub");
        place2.setFromDate(LocalDate.of(2022, Month.AUGUST, 7));

        guideRepository.save(place1);
        guideRepository.save(place2);

        List<Guide> list= new ArrayList<>();
        list.add(place1);
        list.add(place2);

        when(guideRepository.findByFamousPlace("IT hub")).thenReturn(list);
        List<Guide> guides=guideRepository.findByFamousPlace("IT hub");
        assertNotNull(guides);
        assertThat(guides).isNotNull();
        assertEquals(2,guides.size());

    }
    @Test
    @DisplayName("It should update the movie famousPlace with IT hub")
    void updateGuide()
    {
        place1 = new Guide();
        place1.setId(1L);
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST, 7));
        guideRepository.save(place1);

        when(guideRepository.findById(anyLong())).thenReturn(Optional.of(place1));
        when(guideRepository.save(any(Guide.class))).thenReturn(place1);

        Guide updateGuide = guideRepository.findById(place1.getId()).get();
        updateGuide.setFamousPlace("IT hub");
        Guide updatedMovie = guideRepository.save(updateGuide);

        assertEquals("IT hub", updatedMovie.getFamousPlace());
        assertEquals("Vizag", updatedMovie.getCityName());


    }

    @Test
    @DisplayName("It should delete the existing guide")
    void deleteGuide()
    {
        place1 = new Guide();
        place1.setId(1L);
        place1.setCityName("Vizag");
        place1.setFamousPlace("central park");
        place1.setFromDate(LocalDate.of(2018, Month.AUGUST, 7));


        when(guideRepository.findById(anyLong())).thenReturn(Optional.of(place1));
        doNothing().when(guideRepository).delete(any(Guide.class));
        guideService.deleteGuide(1L);
        verify(guideRepository,times(1)).delete(place1);

    }


}
