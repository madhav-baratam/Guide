package com.example.Guide.Guide.service;

import com.example.Guide.Guide.model.Guide;
import com.example.Guide.Guide.repository.GuideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuideServiceTest {
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
    void save()
    {
        when(guideRepository.save(any(Guide.class))).thenReturn(place1);
        Guide newGuide = guideService.save(place1);
        assertNotNull(newGuide);
        assertThat(newGuide.getCityName()).isEqualTo("Vizag");

    }
    @Test
    void getGuide() {

        List<Guide> list = new ArrayList<>();
        list.add(place1);
        list.add(place2);

        when(guideRepository.findAll()).thenReturn(list);

        List<Guide> guides = guideService.getAllGuides();

        assertEquals(2, guides.size());
        assertNotNull(guides);
    }
    @Test
    void getGuideById() {

        when(guideRepository.findById(anyLong())).thenReturn(Optional.of(place1));
        Guide existingGuide = guideService.getGuideBYId(place1.getId());
        assertNotNull(existingGuide);
        assertThat(existingGuide.getId()).isNotEqualTo(null);
    }
    @Test
    void getGuideByIdForException() {

        when(guideRepository.findById(2L)).thenReturn(Optional.of(place1));
        assertThrows(RuntimeException.class, () -> {
            guideService.getGuideBYId(place1.getId());
        });
    }
    @Test
    void updateGuide() {

        when(guideRepository.findById(anyLong())).thenReturn(Optional.of(place1));

        when(guideRepository.save(any(Guide.class))).thenReturn(place1);
     place1.setFamousPlace("Temple");
        Guide exisitingGuide = guideService.updateGuide(place1, place1.getId());

        assertNotNull(exisitingGuide);
        assertEquals("Temple", place1.getFamousPlace());
    }
    @Test
    void deleteGuide() {

        Long guideId = 1L;
        when(guideRepository.findById(anyLong())).thenReturn(Optional.of(place1));
        doNothing().when(guideRepository).delete(any(Guide.class));

        guideService.deleteGuide(guideId);

        verify(guideRepository, times(1)).delete(place1);

    }

}
