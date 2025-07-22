package com.example.dream_shops.service.user;

import com.example.dream_shops.dto.UserDto;
import com.example.dream_shops.model.User;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdatedRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdatedRequest request,Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);
}
