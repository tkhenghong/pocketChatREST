package com.pocketchat.controllers.settings;

import com.pocketchat.db.models.settings.Settings;
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
    public ResponseEntity<Object> addSettings(@Valid @RequestBody Settings settings) {
        Settings savedSettings = settingsService.addSettings(settings);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedSettings.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "")
    public void editSettings(@Valid @RequestBody Settings settings) {
        settingsService.editSettings(settings);
    }

    @DeleteMapping("/{settingsId}")
    public void deleteSettings(@PathVariable String settingsId) {
        settingsService.deleteSettings(settingsId);
    }

    @GetMapping("/{settingsId}")
    public Settings getSingleSettings(@PathVariable String settingsId) {
        return settingsService.getSingleSettings(settingsId);
    }

    @GetMapping("/user/{userId}")
    public Settings getSettingsOfAUser(@PathVariable String userId) {
        return settingsService.getSettingsOfAUser(userId);
    }

}
