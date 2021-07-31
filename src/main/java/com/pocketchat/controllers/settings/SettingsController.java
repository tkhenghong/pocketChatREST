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

    @PatchMapping(value = "")
    public SettingsResponse editSettings(@Valid @RequestBody UpdateSettingsRequest settings) {
        return settingsService.settingsResponseMapper(settingsService.editSettings(settings));
    }

    @GetMapping("/user")
    public SettingsResponse getUserOwnSettings() {
        return settingsService.settingsResponseMapper(settingsService.getUserOwnSettings());
    }
}
