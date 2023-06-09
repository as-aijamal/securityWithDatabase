package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.dto.AuthRequest;
import peaksoft.dto.AuthResponse;
import peaksoft.dto.RegisterRequest;
import peaksoft.service.AuthService;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }


    @PostMapping("/auth")
    public AuthResponse register(@RequestBody AuthRequest authRequest){
        return authService.authenticate(authRequest);
    }



}
