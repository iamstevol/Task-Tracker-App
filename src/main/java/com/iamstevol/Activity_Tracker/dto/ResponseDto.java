package com.iamstevol.Activity_Tracker.dto;

import com.iamstevol.Activity_Tracker.model.User;
import lombok.Data;

@Data
public class ResponseDto {

    private String message;
    private User data;
    private boolean status;
}
