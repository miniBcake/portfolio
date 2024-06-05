package com.example.menbosa.service.protector.user;

import com.example.menbosa.dto.protector.user.UserDTO;
import com.example.menbosa.dto.protector.user.UserSessionDTO;

import java.util.Optional;

public interface UserService {
    void registerUser(UserDTO userDTO);
    UserSessionDTO loginUser(String proMemPhone, String proMemPassword);
}