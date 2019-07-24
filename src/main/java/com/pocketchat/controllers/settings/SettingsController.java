package com.pocketchat.controllers.settings;

import com.pocketchat.dbRepoServices.settings.SettingsRepoService;
import com.pocketchat.dbRepoServices.user.UserRepoService;
import com.pocketchat.models.settings.Settings;
import com.pocketchat.models.user.User;
import com.pocketchat.server.exceptions.settings.SettingsNotFoundException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsRepoService settingsRepoService;

    private final UserRepoService userRepoService;

    @Autowired
    public SettingsController(SettingsRepoService settingsRepoService, UserRepoService userRepoService) {
        this.settingsRepoService = settingsRepoService;
        this.userRepoService = userRepoService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addSettings(@Valid @RequestBody Settings settings) {
        Settings savedSettings = settingsRepoService.save(settings);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedSettings.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "")
    public void editSettings(@Valid @RequestBody Settings settings) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settings.getId());
        validateSettingsNotFound(settingsOptional, settings.getId());
        settingsRepoService.save(settings);
    }

    @DeleteMapping("/{settingsId")
    public void deleteSettings(@PathVariable String settingsId) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settingsId);
        validateSettingsNotFound(settingsOptional, settingsId);
        settingsRepoService.delete(settingsOptional.get());
    }

    @GetMapping("/user/{userId}")
    public Settings getSettingsOfAUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found, userId:-" + userId);
        }

        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(userOptional.get().getId());

        validateSettingsNotFound(settingsOptional, "");

        return settingsOptional.get();
    }

    private void validateSettingsNotFound(Optional<Settings> settingsOptional, String settingsId) {
        if (!settingsOptional.isPresent()) {
            throw new SettingsNotFoundException("Settings not found, id:-" + settingsId);
        }
    }

}
