package com.pocketchat.controllers.settings;

import com.pocketchat.models.controllers.request.settings.CreateSettingsRequest;
import com.pocketchat.models.controllers.request.settings.UpdateSettingsRequest;
import com.pocketchat.models.controllers.response.settings.SettingsResponse;
import com.pocketchat.services.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addSettings(@Valid @RequestBody CreateSettingsRequest settings) {
        SettingsResponse savedSettings = settingsService.addSettings(settings);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedSettings.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "")
    public SettingsResponse editSettings(@Valid @RequestBody UpdateSettingsRequest settings) {
        return settingsService.editSettings(settings);
    }

    @DeleteMapping("/{settingsId}")
    public void deleteSettings(@PathVariable String settingsId) {
        settingsService.deleteSettings(settingsId);
    }

    @GetMapping("/{settingsId}")
    public SettingsResponse getSingleSettings(@PathVariable String settingsId) {
        return settingsService.settingsResponseMapper(settingsService.getSingleSettings(settingsId));
    }

    @GetMapping("/user/{userId}")
    public SettingsResponse getSettingsOfAUser(@PathVariable String userId) {
        return settingsService.getOwnUserSettings(userId);
    }
}
