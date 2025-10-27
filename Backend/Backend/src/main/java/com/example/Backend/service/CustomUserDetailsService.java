//package com.example.Backend.service;

//public class CustomUserDetailsService {

//}
// In: src/main/java/com/example/myproject/service/CustomUserDetailsService.java

package com.example.Backend.service;

import com.example.Backend.entity.User;
import com.example.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service // Tells Spring to manage this class as a Service Bean
public class CustomUserDetailsService implements UserDetailsService {

    // 1. Inject the UserRepository
    // We need this to find the user in our database.
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This is the single method we MUST implement from UserDetailsService.
     * Spring Security will call this method during authentication.
     *
     * @param email This 'username' parameter will actually be the email
     * that the user types into the login form.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        // 2. Find the user by their email
        // We use the repository method we just defined.
        // If the user isn't found, we throw an exception.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with email: " + email));

        // 3. Convert our Role into a Spring Security Authority
        // Spring Security needs a list of 'GrantedAuthority' objects.
        // We get our User's Role, get its name (e.g., "ROLE_ADMIN"),
        // and wrap it in a 'SimpleGrantedAuthority' object.
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        // 4. Return the Spring Security User object
        // We create a new 'User' object from Spring Security (NOT our entity).
        // This object implements the 'UserDetails' interface.
        // We pass it:
        //   a) The username (our user's email)
        //   b) The HASHED password (from our database)
        //   c) The set of authorities (our user's role)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority) // A set containing our single role
        );
    }
}