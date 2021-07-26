package com.pocketchat.controllers.multimedia;

import com.pocketchat.models.controllers.request.multimedia.GetMultimediaListRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.services.multimedia.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Get a list of multimedia in one go.
     * @param getMultimediaListRequest: GetMultimediaListRequest object which has a list of Multimedia object IDs.
     * @return A list of MultimediaResponse object.
     */
    @GetMapping("")
    public List<MultimediaResponse> getMultimediaList(@Valid GetMultimediaListRequest getMultimediaListRequest) {
        return multimediaService.getMultipleMultimedia(getMultimediaListRequest.getMultimediaList()).stream().map(multimediaService::multimediaResponseMapper).collect(Collectors.toList());
    }
}
