package com.collegeManagement.app.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class UnsupportedFormat
{
    public UnsupportedFormat()
    {
        this.setMessage("Invalid file format, Please upload only PDF's");
    }
    private String message;
}
