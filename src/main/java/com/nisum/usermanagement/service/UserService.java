package com.nisum.usermanagement.service;

import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.dto.request.UserRequest;

public interface UserService {

    public UserDto getById(String id);

    public UserDto getByEmail(String email);

    public UserDto create(UserRequest request);
    
}
