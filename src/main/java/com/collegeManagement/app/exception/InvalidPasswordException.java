package com.collegeManagement.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class InvalidPasswordException {

    public InvalidPasswordException()
    {
        this.setMessage("Entered User Credentials are invalid");
    }
    private String message;
}
