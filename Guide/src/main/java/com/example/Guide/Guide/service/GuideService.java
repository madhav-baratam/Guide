package com.example.Guide.Guide.service;

import com.example.Guide.Guide.model.Guide;
import com.example.Guide.Guide.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;
    public Guide save(Guide guide)
    {
        return guideRepository.save(guide);
    }
    public List<Guide> getAllGuides()
    {
        return guideRepository.findAll();
    }
    public Guide getGuideBYId(Long id)
    {
       return guideRepository.findById(id).orElseThrow(()-> new RuntimeException("Guide found for the id"+id));
    }
    public Guide updateGuide(Guide guide,Long id)
    {
        Guide existingGuide= guideRepository.findById(id).get();
        existingGuide.setFamousPlace(guide.getFamousPlace());
        existingGuide.setCityName(guide.getCityName());
        existingGuide.setFromDate(guide.getFromDate());
        return guideRepository.save(existingGuide);
    }
    public void deleteGuide(Long id)
    {
        Guide exitingGuide= guideRepository.findById(id).get();
        guideRepository.delete(exitingGuide);
    }
}
