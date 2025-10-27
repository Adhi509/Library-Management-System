//package com.example.Backend.dto;

//public class JwtAuthResponse {

//}
// In: src/main/java/com/example/myproject/dto/JwtAuthResponse.java

package com.example.Backend.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}