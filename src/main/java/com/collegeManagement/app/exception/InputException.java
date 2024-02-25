package com.collegeManagement.app.exception;

import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
@Component
public class InputException
{
    public InputException()
    {
        this.setMessage("OOPS Error Occured while processing your request, Kindly Check the parameters passed.");
    }
    private String message;

}
