package com.example.dream_shops.service.user;

import com.example.dream_shops.dto.CartDto;
import com.example.dream_shops.dto.OrderDto;
import com.example.dream_shops.dto.UserDto;
import com.example.dream_shops.exception.ALreadyExceptsException;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.User;
import com.example.dream_shops.repository.UserRepository;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdatedRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private  final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user->!userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                  return   userRepository.save(user);
                }).orElseThrow(()->
                   new ALreadyExceptsException("Oops!" + request.getEmail()+"already exits"));
    }


    @Override
    public User updateUser(UserUpdatedRequest request,Long userId) {
        return userRepository.findById(userId)
                .map(existingUser->{
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(()-> new ResourceNotFoundExceotion("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        ()->{throw new ResourceNotFoundExceotion("User not found");});
    }
    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        CartDto cartDto = modelMapper.map(user.getCart(),CartDto.class);
        List<OrderDto> orderDto = Collections.singletonList(modelMapper.map(user.getOrder(), OrderDto.class));
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCart(cartDto);
        userDto.setOrders(orderDto);

        return userDto;
    }
}
