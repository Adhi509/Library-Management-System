//package com.example.Backend.service;

//public class AuthService {

//}
// In: src/main/java/com/example/myproject/service/AuthService.java

package com.example.Backend.service;

import com.example.Backend.dto.LoginDto;
import com.example.Backend.dto.RegisterDto;
import com.example.Backend.entity.Role;
import com.example.Backend.entity.User;
import com.example.Backend.repository.RoleRepository;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String register(RegisterDto registerDto) {
        
        // 1. Check if email already exists
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new RuntimeException("Email is already taken!");
        }

        // 2. Create new User object
        User user = new User();
        user.setEmail(registerDto.getEmail());
        
        // 3. Hash the password
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // 4. Assign default role ("ROLE_MEMBER")
        // ** (This assumes "ROLE_MEMBER" exists in your database!) **
        Role userRole = roleRepository.findByName("ROLE_MEMBER")
                .orElseThrow(() -> new RuntimeException("Error: Default role not found."));
        user.setRole(userRole);

        // 5. Save the new User to the database
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(LoginDto loginDto) {
        
        // 1. Authenticate the user
        // This will use our CustomUserDetailsService and PasswordEncoder
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
            )
        );

        // 2. If authentication is successful, set it in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate the JWT token
        String token = jwtTokenProvider.generateToken(authentication);

        // 4. Return the token
        return token;
    }
}