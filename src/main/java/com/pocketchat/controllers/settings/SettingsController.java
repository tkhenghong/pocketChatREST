package com.pocketchat.controllers.settings;

import com.pocketchat.models.controllers.request.settings.UpdateSettingsRequest;
import com.pocketchat.models.controllers.response.settings.SettingsResponse;
import com.pocketchat.services.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

//    @PostMapping("")
//    public ResponseEntity<Object> addSettings(@Valid @RequestBody CreateSettingsRequest settings) {
//        SettingsResponse savedSettings = settingsService.addSettings(settings);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedSettings.getId())
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }

    @PutMapping(value = "")
    public SettingsResponse editSettings(@Valid @RequestBody UpdateSettingsRequest settings) {
        return settingsService.settingsResponseMapper(settingsService.editSettings(settings));
    }

//    @DeleteMapping("/{settingsId}")
//    public void deleteSettings(@PathVariable String settingsId) {
//        settingsService.deleteSettings(settingsId);
//    }

//    @GetMapping("/{settingsId}")
//    public SettingsResponse getSingleSettings(@PathVariable String settingsId) {
//        return settingsService.settingsResponseMapper(settingsService.getSingleSettings(settingsId));
//    }

    @GetMapping("/user/{userId}")
    public SettingsResponse getUserOwnSettings() {
        return settingsService.settingsResponseMapper(settingsService.getUserOwnSettings());
    }
}
