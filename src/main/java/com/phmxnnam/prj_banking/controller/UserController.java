package com.phmxnnam.prj_banking.controller;

import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.AssignRoleForUserRequest;
import com.phmxnnam.prj_banking.dto.request.UserCreationRequest;
import com.phmxnnam.prj_banking.dto.request.UserUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.UserResponse;
import com.phmxnnam.prj_banking.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("OK")
                .result(userService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("OK")
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("OK")
                .result(userService.getById(id))
                .build();
    }

    @PatchMapping("/assignRoleForUser/{id}")
    ApiResponse<UserResponse> assignRoleForUser(@PathVariable String id, @RequestBody AssignRoleForUserRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("OK")
                .result(userService.assignRoleForUser(id, request))
                .build();
    }

    @PatchMapping("/changePassword/{id}")
    ApiResponse<UserResponse> changePassword(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("OK")
                .result(userService.changePassword(request,id))
                .build();
    }

    @PatchMapping("/turnOnOff/{id}")
    ApiResponse<String> turnOnOffUserById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(userService.turnOnOffUserById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUserById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(userService.deleteUserById(id))
                .build();
    }

}
