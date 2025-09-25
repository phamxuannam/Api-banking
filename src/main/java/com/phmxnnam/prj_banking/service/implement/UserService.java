package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.configuration.PasswordConfig;
import com.phmxnnam.prj_banking.dto.request.AssignRoleForUserRequest;
import com.phmxnnam.prj_banking.dto.request.UserCreationRequest;
import com.phmxnnam.prj_banking.dto.request.UserUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.UserResponse;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.entity.RoleEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.UserMapper;
import com.phmxnnam.prj_banking.repository.CustomerRepository;
import com.phmxnnam.prj_banking.repository.RoleRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    CustomerRepository customerRepository;
    PasswordConfig passwordConfig;


    @Override
    public UserResponse create(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        CustomerEntity customer = customerRepository.findById(request.getCustomer_id()).orElseThrow( () ->
                new AppException(ErrorCode.CUSTOMER_NOT_EXISTS) );

        RoleEntity role = roleRepository.findById("customer").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        Set<RoleEntity> setRole = new HashSet<>();
        setRole.add(role);

        UserEntity user = userMapper.toEntity(request);
        user.setIsActive(1);
        user.setPassword(passwordConfig.passwordEncoder().encode(request.getPassword()));
        user.setCustomer(customer);
        user.setRoles(setRole);

        return userMapper.toResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('permission:read')")
    @Override
    public List<UserResponse> getAll() {
        var context = SecurityContextHolder.getContext().getAuthentication();
        String name = context.getName();
        String role = context.getAuthorities().toString();
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('permission:read')")
    @Override
    public UserResponse getById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        return userMapper.toResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('management')")
    @Override
    public UserResponse assignRoleForUser(String id, AssignRoleForUserRequest request){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        Set<RoleEntity> setRoles = new HashSet<>();
        List<RoleEntity> listRoles = roleRepository.findAllById(request.getRoles());
        for (RoleEntity entity : listRoles){
            if(roleRepository.existsById(entity.getName()) && entity.getIsActive() == 1)
                setRoles.add(entity);
            else throw new AppException(ErrorCode.ROLE_NOT_EXIST);
        }
        user.getRoles().clear();
        user.setRoles(setRoles);

        return userMapper.toResponse(userRepository.save(user));
    }

    @PostAuthorize("returnObject.username == authentication.name ")
    @PreAuthorize("hasRole('teller')")
    @Override
    public UserResponse changePassword(UserUpdateRequest request, String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        userMapper.updateUser(user, request);
        user.setPassword(passwordConfig.passwordEncoder().encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('management')")
    @Override
    public String turnOnOffUserById(String id) {
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

    @PreAuthorize("hasRole('ADMIN') || hasRole('management')")
    @Override
    public String deleteUserById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        user.getRoles().clear();
        userRepository.save(user);

        userRepository.delete(user);
        return "deleted user.";
    }
}
