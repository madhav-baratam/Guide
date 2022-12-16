package com.example.Guide.Guide.controller;

import com.example.Guide.Guide.model.Guide;
import com.example.Guide.Guide.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guide")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guide create(@RequestBody Guide guide)
    {
        return guideService.save(guide);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Guide> read()
    {
        return guideService.getAllGuides();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Guide read(@PathVariable Long id)
    {
        return guideService.getGuideBYId(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id)
    {
        guideService.deleteGuide(id);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Guide update(@PathVariable Long id, @RequestBody Guide guide)
    {
        return guideService.updateGuide(guide, id);
    }

}
