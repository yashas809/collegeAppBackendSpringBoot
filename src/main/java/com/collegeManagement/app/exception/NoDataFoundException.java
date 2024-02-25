package com.collegeManagement.app.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class NoDataFoundException {

    public NoDataFoundException()
    {
        this.setMessage("No Data Found");
    }
    private String message;
}
