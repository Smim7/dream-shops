package com.example.dream_shops.controller;

import com.example.dream_shops.dto.UserDto;
import com.example.dream_shops.exception.ALreadyExceptsException;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.User;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdatedRequest;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.user.IUserService;
import com.example.dream_shops.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

@GetMapping("/{userId}/user")
   public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
       try {
           User user = userService.getUserById(userId);
           UserDto userDto=userService.convertToDto(user);
           return ResponseEntity.ok(new ApiResponse(" success", userDto));
       } catch (ResourceNotFoundExceotion e) {
           return  ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse( e.getMessage(),null));
       }
   }

@PostMapping("/add")
   public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
       try {
           User user = userService.createUser(request);
           UserDto userDto=userService.convertToDto(user);
           return  ResponseEntity.ok(new ApiResponse(" success",userDto));
       } catch (ALreadyExceptsException e) {
           return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
       }
   }

@PutMapping("{userId}/update")
   public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdatedRequest request,
                                                 @PathVariable Long UserId) {
       try {
           User user = userService.updateUser(request,UserId);
           UserDto userDto=userService.convertToDto(user);
           return ResponseEntity.ok(new ApiResponse("update user success!", userDto));
       } catch (ResourceNotFoundExceotion e) {
           return ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(),null));
       }
   }

@DeleteMapping("/{userId}/delete")
   public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long UserId) {
    try {
           userService.deleteUser(UserId);
           return ResponseEntity.ok(new ApiResponse("Delete user success!",null));
    }catch (ResourceNotFoundExceotion e) {
        return ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(),null));
    }
   }
}
