package ru.atomskills.back.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atomskills.back.dto.AuthRequest;
import ru.atomskills.back.dto.RefreshRequest;
import ru.atomskills.back.dto.LoginResponse;
import ru.atomskills.back.models.AppUser;
import ru.atomskills.back.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AppUser> register(@RequestBody AuthRequest request) {
        authenticationService.signup(request.getUsername(), request.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authenticationService.refresh(request.getRefreshToken()));
    }
}
