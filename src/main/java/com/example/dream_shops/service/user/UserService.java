package com.example.dream_shops.service.user;

import com.example.dream_shops.exception.ALreadyExceptsException;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.User;
import com.example.dream_shops.repository.UserRepository;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdatedRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private  final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user->!userRepository.existByEmail(request.getEmail()))
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
}
