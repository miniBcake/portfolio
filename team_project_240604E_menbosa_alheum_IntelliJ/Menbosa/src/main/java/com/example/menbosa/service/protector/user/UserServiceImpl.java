package com.example.menbosa.service.protector.user;

import com.example.menbosa.dto.protector.user.UserDTO;
import com.example.menbosa.dto.protector.user.UserSessionDTO;
import com.example.menbosa.mapper.protector.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserMapper userMapper;

    @Override
    public void registerUser(UserDTO userDTO) {
        userMapper.insertUser(userDTO);
    }

    @Override
    public UserSessionDTO loginUser(String proMemPhone, String proMemPassword) {
//        String proMemPhone = "" + proMemPhoneMid + proMemPhoneBack;
        return userMapper.selectUserInfo(proMemPhone, proMemPassword).orElseThrow(() -> new IllegalStateException("로그인 실패"));
    }
}