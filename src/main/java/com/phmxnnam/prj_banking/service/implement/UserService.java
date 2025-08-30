package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.configuration.PasswordConfig;
import com.phmxnnam.prj_banking.dto.request.UserCreationRequest;
import com.phmxnnam.prj_banking.dto.request.UserUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.UserResponse;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.UserMapper;
import com.phmxnnam.prj_banking.repository.CustomerRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    CustomerRepository customerRepository;
    PasswordConfig passwordConfig;

    @Override
    public UserResponse create(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        CustomerEntity customer = customerRepository.findById(request.getCustomer_id()).orElseThrow( () ->
                new AppException(ErrorCode.CUSTOMER_NOT_EXISTS) );

        UserEntity user = userMapper.toEntity(request);
        user.setIsActive(1);
        user.setPassword(passwordConfig.passwordEncoder().encode(request.getPassword()));
        user.setCustomer(customer);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Override
    public UserResponse getById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse changePassword(UserUpdateRequest request, String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        userMapper.updateUser(user, request);
        user.setPassword(passwordConfig.passwordEncoder().encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public String turnOnOfUserById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        if(user.getIsActive() == 1){
            user.setIsActive(0);
            userRepository.save(user);
            return "turned off user.";
        } else {
            user.setIsActive(1);
            userRepository.save(user);
            return "turned on user.";
        }
    }

    @Override
    public String deleteUserById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

//        user.getRoles().clear();
//        userRepository.save(user);

        userRepository.delete(user);
        return "deleted user.";
    }
}
