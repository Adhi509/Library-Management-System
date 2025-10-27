//package com.example.Backend.dto;

// class RegisterDto {

//}
// In: src/main/java/com/example/myproject/dto/RegisterDto.java

package com.example.Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be a valid email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}