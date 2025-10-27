//package com.example.Backend.dto;

//public class LoginDto {

//}
// In: src/main/java/com/example/myproject/dto/LoginDto.java

package com.example.Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be a valid email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
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