package com.iamstevol.Activity_Tracker.service.impl;

import com.iamstevol.Activity_Tracker.dto.LoginDto;
import com.iamstevol.Activity_Tracker.dto.ResponseDto;
import com.iamstevol.Activity_Tracker.dto.UserRequestDto;
import com.iamstevol.Activity_Tracker.exception.CustomException;
import com.iamstevol.Activity_Tracker.model.User;
import com.iamstevol.Activity_Tracker.repository.UserRepository;
import com.iamstevol.Activity_Tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loginUser(UserRequestDto loginDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()));
        ResponseDto response = new ResponseDto();

        if(user.isPresent()) {
            response.setStatus(true);
            response.setData(user.get());
            response.setMessage("Login successful");
            return response.getData();
        } else {
            response.setMessage("Invalid Login");
            return response.getData();
        }
    }

    @Override
    public User registerUser(UserRequestDto userRequestDto) {

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setUsername(userRequestDto.getUsername());
        return userRepository.save(user);
    }
}
