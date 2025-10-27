//package com.example.Backend.exception;

//public class ErrorDetails {

//}
// In: src/main/java/com/example/myproject/exception/ErrorDetails.java

package com.example.Backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
 // --- ADD THIS CONSTRUCTOR ---
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // --- ADD GETTERS ---
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
    
}
