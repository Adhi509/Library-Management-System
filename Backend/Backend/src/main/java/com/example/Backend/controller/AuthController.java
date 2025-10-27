//package com.example.Backend.controller;

//public class AuthController {

//}
// In: src/main/java/com/example/myproject/controller/AuthController.java

package com.example.Backend.controller;

import com.example.Backend.dto.JwtAuthResponse;
import com.example.Backend.dto.LoginDto;
import com.example.Backend.dto.RegisterDto;
import com.example.Backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Base URL for this controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
        
        return ResponseEntity.ok(jwtAuthResponse);
    }
}