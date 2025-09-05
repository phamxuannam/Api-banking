package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.RoleRequest;
import com.phmxnnam.prj_banking.dto.response.RoleResponse;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.entity.PermissionEntity;
import com.phmxnnam.prj_banking.entity.RoleEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.PermissionMapper;
import com.phmxnnam.prj_banking.mapper.RoleMapper;
import com.phmxnnam.prj_banking.repository.PermissionRepository;
import com.phmxnnam.prj_banking.repository.RoleRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;
    UserRepository userRepository;
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    @Override
    public RoleResponse create(RoleRequest request) {
        if(roleRepository.existsById(request.getName())) throw new AppException(ErrorCode.ROLE_EXISTED);
        RoleEntity role = roleMapper.toEntity(request);
        role.setIsActive(1);
        Set<PermissionEntity> setPermission = new HashSet<>();

        List<PermissionEntity> permission = permissionRepository.findAllById(request.getPermissions());
        for (PermissionEntity entity : permission) {
            if(permissionRepository.existsById(entity.getName()) && entity.getIsActive() == 1)
                setPermission.add(entity);
            else throw new AppException(ErrorCode.PERMISSION_NOT_EXISTS);
        }
        role.setPermissions(setPermission);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

    @Override
    public String turnOnOffRole(String id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXIST));
        List<UserEntity> listUser = userRepository.findAllByRoleName(id);
        boolean on = true;
        int active = 1;
        if(role.getIsActive() == 1){
            on = false;
            active = 0;
        }
        role.setIsActive(active);
        roleRepository.save(role);
        for(UserEntity user : listUser){
            user.setIsActive(active);
            userRepository.save(user);
        }
        if(on) return "turned on role.";
        else return "turned off role.";
    }

    @Override
    public String deleteRole(String id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        role.getUsers().clear();
        roleRepository.save(role);

        roleRepository.deleteById(id);
        return "Deleted Role.";
    }
}
