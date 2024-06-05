package com.example.menbosa.mapper.protector.user;

import com.example.menbosa.dto.protector.user.UserDTO;
import com.example.menbosa.dto.protector.user.UserSessionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void insertUser(UserDTO userDTO);

    Optional<Long> selectId(@Param("proMemPhone") String proMemPhone, @Param("password") String password);

    Optional<UserSessionDTO> selectUserInfo(String proMemPhone, String proMemPassword);
}
