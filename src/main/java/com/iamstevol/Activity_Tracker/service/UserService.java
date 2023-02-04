package com.iamstevol.Activity_Tracker.service;

import com.iamstevol.Activity_Tracker.dto.LoginDto;
import com.iamstevol.Activity_Tracker.dto.ResponseDto;
import com.iamstevol.Activity_Tracker.dto.UserRequestDto;
import com.iamstevol.Activity_Tracker.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User loginUser(UserRequestDto loginDto);
    User registerUser(UserRequestDto userRequestDto);

}
