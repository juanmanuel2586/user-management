package com.nisum.usermanagement.service;

import com.nisum.usermanagement.dto.UserDto;

public interface UserService {

    public UserDto getById(String id);

    public UserDto getByEmail(String email);
    
}
