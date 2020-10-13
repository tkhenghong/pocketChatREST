package com.pocketchat.controllers.multimedia;

import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.services.multimedia.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    @GetMapping("/{multimediaId}")
    public MultimediaResponse getSingleMultimedia(@PathVariable String multimediaId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getSingleMultimedia(multimediaId));
    }
}
